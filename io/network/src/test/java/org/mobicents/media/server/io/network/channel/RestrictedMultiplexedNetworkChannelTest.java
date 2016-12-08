/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2016, Telestax Inc and individual contributors
 * by the @authors tag. 
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.mobicents.media.server.io.network.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import org.apache.log4j.Logger;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * @author Henrique Rosa (henrique.rosa@telestax.com)
 *
 */
public class RestrictedMultiplexedNetworkChannelTest {

    private static final Logger log = Logger.getLogger(RestrictedMultiplexedNetworkChannelTest.class);

    private ByteBuffer agentBuffer;
    private DatagramChannel callAgent;

    @Before
    public void before() throws IOException {
        agentBuffer = ByteBuffer.allocate(200);
        callAgent = DatagramChannel.open();
        callAgent.configureBlocking(false);
        callAgent.bind(new InetSocketAddress("127.0.0.1", 0));
    }

    @After
    public void after() {
        if (callAgent != null && callAgent.isOpen()) {
            try {
                callAgent.close();
            } catch (IOException e) {
                log.error("Could not close Call Agent", e);
            }
        }
    }

    @Test
    public void testSendReceive() {
        // given
        final byte[] ping = "ping".getBytes();
        final byte[] pong = "pong".getBytes();
        final InetSocketAddress address = new InetSocketAddress("127.0.0.1", 0);
        final NetworkGuard guard = mock(NetworkGuard.class);
        final PacketHandler handler = mock(PacketHandler.class);

        try (final MultiplexedNetworkChannel channel = new MultiplexedNetworkChannel(guard, handler)) {
            // when
            channel.open();
            channel.bind(address);

            when(handler.canHandle(ping)).thenReturn(true);
            when(handler.handle(ping, channel.getLocalAddress(), (InetSocketAddress) callAgent.getLocalAddress())).thenReturn(pong);
            when(guard.isSecure(channel, (InetSocketAddress) callAgent.getLocalAddress())).thenReturn(true);

            callAgent.send(ByteBuffer.wrap(ping), channel.getLocalAddress());
            channel.receive();

            callAgent.receive(agentBuffer);

            final byte[] response = new byte[pong.length];
            agentBuffer.flip();
            agentBuffer.get(response);

            // then
            assertEquals("pong", new String(response));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}
