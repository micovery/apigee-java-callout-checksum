// Copyright 2024 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.apigee.callouts;

import java.io.ByteArrayInputStream;
import java.util.HashMap;


import static org.junit.jupiter.api.Assertions.*;

class ChecksumTest {
    @org.junit.jupiter.api.Test
    void getSHA256() throws Exception {
        Checksum callout = new Checksum(new HashMap());
        String messageBody = "Hello World";
        String result = callout.getSHA256(new ByteArrayInputStream(messageBody.getBytes() ));
        assertEquals("a591a6d40bf420404a011733cfb7b190d62c65bf0bcda32b57b277d9ad9f146e", result);
    }
}