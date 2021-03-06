/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2017, Telestax Inc and individual contributors
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.restcomm.javax.media.mscontrol.networkconnection.fsm;

/**
 *
 * @author kulikov
 */
public interface ConnectionState {    
    public final static String NULL = "NULL";
    public final static String OPENING = "OPENING";
    public final static String OPEN= "OPEN";
    public final static String HALF_OPEN = "HALF_OPEN";
    public final static String CANCELED = "CANCELED";
    public final static String MODIFYING = "MODIFYING";
    public final static String CLOSING = "CLOSING";
    public final static String FAILED = "FAILED";
    public final static String INVALID = "INVALID";    
}
