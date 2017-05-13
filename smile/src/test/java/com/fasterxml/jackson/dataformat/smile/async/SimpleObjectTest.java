package com.fasterxml.jackson.dataformat.smile.async;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonParser.NumberType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;
import com.fasterxml.jackson.dataformat.smile.SmileParser;

public class SimpleObjectTest extends AsyncTestBase
{
    private final SmileFactory F_REQ_HEADERS = new SmileFactory();
    {
        F_REQ_HEADERS.enable(SmileParser.Feature.REQUIRE_HEADER);
    }

    private final SmileFactory F_NO_HEADERS = new SmileFactory();
    {
        F_NO_HEADERS.disable(SmileParser.Feature.REQUIRE_HEADER);
    }

    /*
    /**********************************************************************
    /* Test methods
    /**********************************************************************
     */

    /*
    @JsonPropertyOrder(alphabetic=true)
    static class BooleanBean {
        public boolean a, b, ac, abcde, e;
    }
     */
    
    public void testBooleans() throws IOException
    {
        final SmileFactory f = new SmileFactory();
        f.enable(SmileParser.Feature.REQUIRE_HEADER);
        byte[] data = _smileDoc(aposToQuotes("{ 'a':true, 'b':false, 'ac':true, 'abcde':true, 'e':false }"), true);
        // first, no offsets
        _testBooleans(f, data, 0, 100);
        _testBooleans(f, data, 0, 3);
        _testBooleans(f, data, 0, 1);

        // then with some
        _testBooleans(f, data, 1, 100);
        _testBooleans(f, data, 1, 3);
        _testBooleans(f, data, 1, 1);
    }

    private void _testBooleans(SmileFactory f,
            byte[] data, int offset, int readSize) throws IOException
    {
        AsyncReaderWrapper r = asyncForBytes(f, readSize, data, offset);
        // start with "no token"
        assertNull(r.currentToken());
        assertToken(JsonToken.START_OBJECT, r.nextToken());

        assertToken(JsonToken.FIELD_NAME, r.nextToken());
        assertEquals("a", r.currentText());
        assertToken(JsonToken.VALUE_TRUE, r.nextToken());

        assertToken(JsonToken.FIELD_NAME, r.nextToken());
        assertEquals("b", r.currentText());
        assertToken(JsonToken.VALUE_FALSE, r.nextToken());

        assertToken(JsonToken.FIELD_NAME, r.nextToken());
        assertEquals("ac", r.currentText());
        assertToken(JsonToken.VALUE_TRUE, r.nextToken());

        assertToken(JsonToken.FIELD_NAME, r.nextToken());
        assertEquals("abcde", r.currentText());
        assertToken(JsonToken.VALUE_TRUE, r.nextToken());

        assertToken(JsonToken.FIELD_NAME, r.nextToken());
        assertEquals("e", r.currentText());
        assertToken(JsonToken.VALUE_FALSE, r.nextToken());

        // and for fun let's verify can't access this as number or binary
        try {
            r.getDoubleValue();
            fail("Should not pass");
        } catch (JsonProcessingException e) {
            verifyException(e, "Current token (VALUE_FALSE) not numeric");
        }
        try {
            r.parser().getBinaryValue();
            fail("Should not pass");
        } catch (JsonProcessingException e) {
            verifyException(e, "Current token (VALUE_FALSE) not");
            verifyException(e, "can not access as binary");
        }

        assertToken(JsonToken.END_OBJECT, r.nextToken());

        // and end up with "no token" as well
        assertNull(r.nextToken());
        assertTrue(r.isClosed());
    }

    /*
    static class  {
        public int i1;
        public double doubley;
        public BigDecimal biggieDecimal;
    }
    */

    private final int NUMBER_EXP_I = -123456789;
    private final double NUMBER_EXP_D = 1024798.125;
    private final BigDecimal NUMBER_EXP_BD = new BigDecimal("1243565768679065.1247305834");

    public void testNumbers() throws IOException
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(100);
        SmileFactory f = F_REQ_HEADERS;
        JsonGenerator g = f.createGenerator(bytes);
        g.writeStartObject();
        g.writeNumberField("i1", NUMBER_EXP_I);
        g.writeNumberField("doubley", NUMBER_EXP_D);
        g.writeNumberField("biggieDecimal", NUMBER_EXP_BD);
        g.writeEndObject();
        g.close();
        byte[] data = bytes.toByteArray();

        // first, no offsets
        _testNumbers(f, data, 0, 100);
        _testNumbers(f, data, 0, 3);
        _testNumbers(f, data, 0, 1);

        // then with some
        _testNumbers(f, data, 1, 100);
        _testNumbers(f, data, 1, 3);
        _testNumbers(f, data, 1, 1);
    }

    private void _testNumbers(SmileFactory f,
            byte[] data, int offset, int readSize) throws IOException
    {
        AsyncReaderWrapper r = asyncForBytes(f, readSize, data, offset);
        // start with "no token"
        assertNull(r.currentToken());
        assertToken(JsonToken.START_OBJECT, r.nextToken());

        assertToken(JsonToken.FIELD_NAME, r.nextToken());
        assertEquals("i1", r.currentText());
        assertToken(JsonToken.VALUE_NUMBER_INT, r.nextToken());
        assertEquals(NumberType.INT, r.getNumberType());
        assertEquals(NUMBER_EXP_I, r.getIntValue());
        assertEquals((double)NUMBER_EXP_I, r.getDoubleValue());

        assertToken(JsonToken.FIELD_NAME, r.nextToken());
        assertEquals("doubley", r.currentText());
        assertToken(JsonToken.VALUE_NUMBER_FLOAT, r.nextToken());
        assertEquals(NumberType.DOUBLE, r.getNumberType());
        assertEquals(NUMBER_EXP_D, r.getDoubleValue());
        assertEquals((long) NUMBER_EXP_D, r.getLongValue());

        assertToken(JsonToken.FIELD_NAME, r.nextToken());
        assertEquals("biggieDecimal", r.currentText());
        assertToken(JsonToken.VALUE_NUMBER_FLOAT, r.nextToken());
        assertEquals(NumberType.BIG_DECIMAL, r.getNumberType());
        assertEquals(NUMBER_EXP_BD, r.getBigDecimalValue());
        assertEquals(""+NUMBER_EXP_BD, r.currentText());

        assertToken(JsonToken.END_OBJECT, r.nextToken());

        // and end up with "no token" as well
        assertNull(r.nextToken());
        assertTrue(r.isClosed());
    }
}
