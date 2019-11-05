/*
 * Copyright 2019-2019 Gryphon Zone
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package zone.gryphon.canary;


import org.junit.Test;

import java.beans.ConstructorProperties;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.failBecauseExceptionWasNotThrown;

@SuppressWarnings("ResultOfMethodCallIgnored")
public class SimplePojoTest {

    private final SimplePojo foo = SimplePojo.builder()
            .id("foo")
            .build();

    private final SimplePojo alsoFoo = SimplePojo.builder()
            .id("foo")
            .build();

    private final SimplePojo bar = SimplePojo.builder()
            .id("bar")
            .build();

    @Test
    public void testConstructor() throws Exception {
        Constructor<SimplePojo> declaredConstructor = SimplePojo.class.getDeclaredConstructor(String.class);
        assertThat(declaredConstructor).isNotNull();

        ConstructorProperties annotation = declaredConstructor.getAnnotation(ConstructorProperties.class);
        assertThat(annotation).isNotNull();

        assertThat(annotation.value()).containsExactly("id");

        assertThat(declaredConstructor.newInstance("foo")).isInstanceOf(SimplePojo.class);

        try {
            assertThat(declaredConstructor.newInstance(new Object[]{null})).isInstanceOf(SimplePojo.class);
            failBecauseExceptionWasNotThrown(InvocationTargetException.class);
        } catch (InvocationTargetException e) {
            assertThat(e).hasCauseInstanceOf(NullPointerException.class);
            assertThat(e.getCause()).hasMessageContaining("id");
        }
    }

    @Test
    public void testEquals() {
        assertThat(foo).isNotEqualTo(null);
        assertThat(foo).isNotEqualTo("foo");
        assertThat(foo).isNotEqualTo(bar);
        assertThat(foo).isEqualTo(foo);
        assertThat(foo).isEqualTo(alsoFoo);
        assertThat(foo).isEqualTo(foo.toBuilder().build());
    }

    @Test
    public void testToString() {
        assertThat(foo.toString()).isNotBlank();
        assertThat(foo.toBuilder().toString()).isNotBlank();
    }

    @Test
    public void testGetId() {
        assertThat(foo.getId()).isEqualTo("foo");
    }

    @Test
    public void testHashCode() {
        assertThat(foo.hashCode()).isEqualTo(alsoFoo.hashCode());
    }

    @Test(expected = NullPointerException.class)
    public void testBuilderWithNull() {
        SimplePojo.builder().id(null);
    }


}