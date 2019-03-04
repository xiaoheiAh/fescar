/*
 *  Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.alibaba.fescar.common.util;

import java.nio.charset.Charset;
import org.junit.Test;

import javax.sql.rowset.serial.SerialBlob;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author melon.zhao
 * @since 2019/2/20
 */
public class StringUtilsTest {

    @Test
    public void testIsEmpty() {
        assertThat(StringUtils.isEmpty(null)).isTrue();
        assertThat(StringUtils.isEmpty("abc")).isFalse();
        assertThat(StringUtils.isEmpty("")).isTrue();
        assertThat(StringUtils.isEmpty(" ")).isFalse();
    }

    @Test
    public void testString2blob() throws SQLException {
        assertThat(StringUtils.string2blob(null)).isNull();
        String[] strs = new String[]{"abc", "", " "};
        for (String str : strs) {
            assertThat(StringUtils.string2blob(str)).isEqualTo(new SerialBlob(str.getBytes(Charset.forName("UTF-8"))));
        }
    }

    @Test
    public void testBlob2string() throws SQLException {
        String[] strs = new String[]{"abc", " "};
        for (String str : strs) {
            assertThat(StringUtils.blob2string(new SerialBlob(str.getBytes()))).isEqualTo(str);

        }
    }

    @Test
    public void testInputStream2String() {
        try {
            InputStream inputStream = StringUtilsTest.class.getClassLoader().getResourceAsStream("test.txt");
            assertThat(StringUtils.inputStream2String(inputStream)).isEqualTo("abc\n"
                    + ":\"klsdf\n"
                    + "2ks,x:\".,-3sd˚ø≤ø¬≥");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
