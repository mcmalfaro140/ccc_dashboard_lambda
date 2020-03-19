package com.ccc.lambda;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.ccc.logs.notifications.LambdaFunctionHandler;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {
    private static Object input;

    @BeforeClass
    public static void createInput() throws IOException {
        // TODO: set up your sample input here.
    	Map<String, Map<String, String>> map1 = new HashMap<String, Map<String, String>>();
    	Map<String, String> submap1 = new HashMap<String, String>();
    	
    	submap1.put("data", "H4sIAAAAAAAAAM1QwWrcMBD9lUVn20iyZVm+mXSbQmkLuyE51CE49tQR2JKRtckuy/57xto0uQZCIAfBzJs372nekYwwz00PV4cJSEm+VVfV3a/1dltdrklE7JMBhzBjXDGWilzwAuHB9pfO7iaceJj9Gdl6B834Ar00EZl393Pr9OS1Nd/14MHNpPxLvNN9j9K3YXX9CMYv+JHoDhVSwaksCppmWa5kSosiU5nIU8kovlzmMuU0Z0hSQkkueZ4VClno5zUe5JsR/8aELKQUSkiq0uj/oSh/rN9oNSlrwimnMWUxYyvGS8HLVCa4U5OoJgM8whBY683mzyZg/gGv6wL44P0UG21jQQWNYQ9tzANn7FokLFZo9BMOgb3U182wg5qcFm27hBAmrR2Ttm2TZtJJa413dhjAJT/sCBev7Vn4fEfYqswKnLNu9QpGi5TxsPeB0MG/ZjdgfULDj6WrPjfdm2rz+4uFu3pqnNGmf1e6t6dnT3jSjkwDAAA=");
    	map1.put("awslogs", submap1);
    	
        input = map1;
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("LogMessager");

        return ctx;
    }

    @Test
    public void testLambdaFunctionHandler() {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();
        Context ctx = createContext();

        String output1 = handler.handleRequest(input, ctx);

        // TODO: validate output here if needed.
        Assert.assertEquals("", output1);
    }
}