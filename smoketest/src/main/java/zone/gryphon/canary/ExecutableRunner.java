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

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutableRunner {

    /**
     * Invoke the given executable and return the result.
     *
     * @param executable The executable to invoke
     * @return The result of the operation
     */
    public static Object invoke(Executable executable) {
        SimplePojo pojo = SimplePojo.builder()
            .id(UUID.randomUUID().toString())
            .build();

        log.info("[slf4j] About to invoke method");
        return executable.execute(pojo);
    }


}
