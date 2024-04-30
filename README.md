<!--
 Copyright 2024 Google LLC

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
-->

# Apigee Checksum Java Callout

This is a custom Apigee Java Callout that calculates the SHA256 checksum of a message body, and stores the
result value in a flow variable. 

This Java Callout is only for demonstration purposes, to showcase how to build Java Callouts in Apigee.
You don't actually need to use a Java callout to calculate a SHA256. 

The Apigee X built-in AssignMessage policy supports template functions which allow you to do that.



## How to configure it

This policy takes two properties as inputs 

```xml
    <Properties>
        <Property name="message-ref">request</Property>
        <Property name="output-ref">request.header.checksum</Property>
    </Properties>
```


| Property     | Description                                                                                     |
|--------------|-------------------------------------------------------------------------------------------------|
| message-ref  | Flow variable pointing to the message object for which we want to compute the payload's SHA256. |
| output-ref   | Flow variable where we want to store the result of the computation                              |




## Sample Java Callout policy

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<JavaCallout continueOnError="false" enabled="true" name="JC-GraphQL">
  <DisplayName>JC-Checksum</DisplayName>
  <Properties>
    <Property name="message-ref">request</Property>
    <Property name="output-ref">request.header.checksum</Property>
  </Properties>
  <ClassName>com.google.apigee.callouts.Checksum</ClassName>
  <ResourceURL>java://apigee-java-callout-checksum.jar</ResourceURL>
</JavaCallout>
```


## Support
This is not an officially supported Google product