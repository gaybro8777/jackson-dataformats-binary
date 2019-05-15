/*
 * Copyright 2012-2016 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at:
 *
 *     http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */

package com.fasterxml.jackson.dataformat.ion;

import java.io.IOException;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.ion.IonObjectMapper;

import com.amazon.ion.IonDatagram;
import com.amazon.ion.IonLoader;
import com.amazon.ion.IonSystem;
import com.amazon.ion.IonType;
import com.amazon.ion.IonValue;
import com.amazon.ion.system.IonSystemBuilder;

public class IonTimestampRoundTripTest {
    IonSystem ionSystem = IonSystemBuilder.standard().build();
      
    @Test
    public void testDateRoundTrip() throws JsonGenerationException, JsonMappingException, IOException {
        Date date = new Date();
        IonObjectMapper m = IonObjectMapper.builder()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        
        String val = m.writeValueAsString(date);
        IonLoader loader = ionSystem.newLoader();
        IonDatagram dgram = loader.load(val);
        IonValue ionVal = dgram.iterator().next();
        
        Assert.assertEquals("Expected date to be serialized into an IonTimestamp", IonType.TIMESTAMP, ionVal.getType());
        
        Date returned = m.readValue(val, Date.class);
        Assert.assertEquals("Date result not the same as serialized value.", date, returned);
        
    }
    
    @Test
    public void testMillisCompatibility() throws JsonGenerationException, JsonMappingException, IOException {
        Date date = new Date();
        IonObjectMapper m = IonObjectMapper.builder()
                .enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        
        String val = m.writeValueAsString(date);
        Date returned = m.readValue(val, Date.class);
        Assert.assertEquals("Date result not the same as serialized value.", date, returned);
        
    }
    
    /**
     * Test if the JoiMapper can read all forms of dates generated by non-JoiMappers
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @Test
    public void testNonJoiCompatibility() throws IOException {
        Date date = new Date();
        JsonMapper nonJoiMillis = new JsonMapper();
        JsonMapper nonJoiM = JsonMapper.builder()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .build();
        IonObjectMapper joiM = new IonObjectMapper();
        
        String dateInMillis = nonJoiMillis.writeValueAsString(date);
        String dateFormatted = nonJoiM.writeValueAsString(date);
        
        Date millisBack = joiM.readValue(dateInMillis, Date.class);
        Date formattedBack = joiM.readValue(dateFormatted, Date.class);
        
        Assert.assertEquals("Date result not the same as serialized value.", date, millisBack);
        Assert.assertEquals("Date result not the same as serialized value.", date, formattedBack);
        
    }
}
