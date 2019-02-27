/*
 * Copyright (c) 2016, 2019, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package jdk.jfr.api.metadata.settingdescriptor;

import java.util.Objects;

import jdk.jfr.EventType;
import jdk.jfr.SettingDescriptor;
import jdk.testlibrary.Asserts;
import jdk.testlibrary.jfr.Events;
import jdk.testlibrary.jfr.BaseEvent;
import jdk.testlibrary.jfr.PlainSetting;
import jdk.testlibrary.jfr.AnnotatedSetting;
import jdk.testlibrary.jfr.CustomEvent;

/*
 * @test
 * @summary Test SettingDescriptor.getDescription()
 * @key jfr
 * @library /lib/testlibrary
 * @run main/othervm jdk.jfr.api.metadata.settingdescriptor.TestGetDescription
 */
public class TestGetDescription {

    public static void main(String[] args) throws Exception {
        EventType type = EventType.getEventType(CustomEvent.class);

        SettingDescriptor plain = Events.getSetting(type, "plain");
        Asserts.assertNull(plain.getDescription());

        SettingDescriptor annotatedType = Events.getSetting(type, "annotatedType");
        Asserts.assertEquals(annotatedType.getDescription(), AnnotatedSetting.DESCRIPTION);

        SettingDescriptor newName = Events.getSetting(type, "newName");
        Asserts.assertEquals(newName.getDescription(), CustomEvent.DESCRIPTION_OF_AN_ANNOTATED_METHOD);

        SettingDescriptor overridden = Events.getSetting(type, "overridden");
        Asserts.assertNull(overridden.getDescription());

        SettingDescriptor protectedBase = Events.getSetting(type, "protectedBase");
        Asserts.assertEquals(protectedBase.getDescription(), "Description of protected base");

        SettingDescriptor publicBase = Events.getSetting(type, "publicBase");
        Asserts.assertEquals(publicBase.getDescription(), AnnotatedSetting.DESCRIPTION);

        SettingDescriptor packageProtectedBase = Events.getSetting(type, "packageProtectedBase");
        Asserts.assertNull(packageProtectedBase.getDescription());

        CustomEvent.assertOnDisk((x, y) -> Objects.equals(x.getDescription(), y.getDescription()) ? 0 : 1);
    }
}
