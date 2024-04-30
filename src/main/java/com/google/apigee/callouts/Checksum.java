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

import com.apigee.flow.execution.ExecutionContext;
import com.apigee.flow.execution.ExecutionResult;
import com.apigee.flow.execution.spi.Execution;
import com.apigee.flow.message.Message;
import com.apigee.flow.message.MessageContext;
import com.google.apigee.callouts.util.Debug;
import com.google.apigee.callouts.util.Logger;
import com.google.apigee.callouts.util.VarResolver;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;


import java.io.*;
import java.security.MessageDigest;
import java.util.*;

public class Checksum implements Execution {
    public static final String CALLOUT_VAR_PREFIX = "checksum";
    public static final String PROP_MESSAGE_REF = "message-ref";
    public static final String PROP_OUTPUT_REF = "output-ref";


    private final Map properties;

    public Checksum(Map properties) throws UnsupportedEncodingException {
        this.properties = properties;
    }

    private void saveOutputs(MessageContext msgCtx, Logger logger) {
        msgCtx.setVariable(CALLOUT_VAR_PREFIX + ".info.stdout", new String(logger.stdoutOS.toByteArray(), StandardCharsets.UTF_8));
        msgCtx.setVariable(CALLOUT_VAR_PREFIX + ".info.stderr", new String(logger.stderrOS.toByteArray(), StandardCharsets.UTF_8));
    }

    public ExecutionResult execute(MessageContext messageContext, ExecutionContext executionContext) {
        Logger logger = new Logger();

        try {
            VarResolver vars = new VarResolver(messageContext, properties);
            Debug dbg = new Debug(messageContext, CALLOUT_VAR_PREFIX);

            String messageVariable = vars.getProp(PROP_MESSAGE_REF);
            Message msg = messageContext.getVariable(messageVariable);
            String outputRef = vars.getProp(PROP_OUTPUT_REF);

            String sha256 = getSHA256(msg.getContentAsStream());
            messageContext.setVariable(outputRef, sha256);

            return ExecutionResult.SUCCESS;
        } catch (Error | Exception e) {
            e.printStackTrace(logger.stderr);
            return ExecutionResult.ABORT;
        } finally {
            saveOutputs(messageContext, logger);
        }
    }

    public String getSHA256(InputStream stream) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = stream.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead);
        }

        byte[] digestBytes = digest.digest();
        return new String(Hex.encodeHex(digestBytes));
    }
}
