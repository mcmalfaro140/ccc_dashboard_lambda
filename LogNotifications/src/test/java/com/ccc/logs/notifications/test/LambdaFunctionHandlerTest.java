package com.ccc.logs.notifications.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.ccc.logs.notifications.LambdaFunctionHandler;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {
    private static Object errorInput;
    private static Object warnInput;
    private static Object infoInput;

    @BeforeClass
    public static void createInput() throws IOException {
    	LambdaFunctionHandlerTest.errorInput = LambdaFunctionHandlerTest._makeInputMap("H4sIAAAAAAAAAFWQX2uDMBTFv0rJs4pJjH/6VjbXwRiDtuxlluHinQ2okRi7luJ3X6zp6sjTufd37j03F1RD1+Ul7M4toCV6XO1Wn6/pdrtap8hB8qcBZcoYkwRjykJGYlOuZLlWsm9NR0Onp8pWK8hrW7LCQV3/1XElWi1k8yQqDapDyw+klShLM3p/taZHaPRYvyBRmAmU0SBgOEx8SiMaEkaSmGFCzQtj80ISxUFCfOpHEY1wgJPANHBo9mlhDtJ5bbJhZiCfJIyQOHZuh5rxl0xlzWKR3eEMLZZG/3NkyLlxFRyhsky62bxtZj19MJcWtnnQunUbIV3mM9+FE3A3mLF1wa+gDXC1mwAvcLb+Ub3nVQ8ZmpDhnkGOH2Y5LmuPc+7lrfC4bLSSVQXKe5Y1PPzJ+eLpduu2yp8B4xA4aQsU8J33lZ5CDGjYD79TH3PHKAIAAA==");
    	LambdaFunctionHandlerTest.warnInput = LambdaFunctionHandlerTest._makeInputMap("H4sIAAAAAAAAAFWQX0+DMBTFv8rS50FoSxnsjeicidEHt+iDLAbLlTUplJQytyx8d8voHD6ec3/n/jujCto2L2F7agAt0X26TT+fV5tNul6hOVI/NWhrY0wSjCmLGImtLVW51qprbMVAa0ZnYzTklbOcmKO2+2q5Fo0Rqn4Q0oBu0fIDGS3K0rbeXaKrA9Rm8M9IFLYDZTQMWZSQaBGFOCGEhIQsWLzAEcUkxJRiSliC4wDThJIgjmhE7I52nhH2IJNXdjfM4jAJGA0CGtP59VDb/pzprJ7NshucodnS6n+JDM2vnIQDSMe8p68vk5LZ20MLV9sb03i1UB4LWODBEbiXTNiq4BfQzb/E7fwnOLn8oN5y2UGGRqS/raCGfzmOq8rnnPt5I3yuaqOVlKD9R1XB3Z+cDh5Pd2mn8AQYmsDROKCA77yTZlyiR/2u/wWujouqJwIAAA==");
    	LambdaFunctionHandlerTest.infoInput = LambdaFunctionHandlerTest._makeInputMap("H4sIAAAAAAAAAF1Qy07DMBD8lcgXLm2IkziP3gqUigNwSG9NhUy6pJZcO7K3pVD139lAKgqSJXtndmdnfGRb8F62sPjogE3Y3XQxfXmcVdV0PmMjZt8NOII5j0vOE5GJuCBY23bu7K4jBsHjD1KhA7kdoKEYMb979Y1THSpr7pVGcJ5NlgydaluSXn2PzvZgsMePTK1JIRFJmgqRiDxPOZ2yTHoDcZyKsojjnKdZWRZ5luQZ4aKIo7xIMp7RPlQUCOWWvHFRpGVEI1kRlaNzUJI/1q42QVD/NtcsmFD9Z6Jmo3Ofhj3ooefh6f75gsINBV0P3AaxGxtlxyIS0RgO0Iz5pYztMw+91rWh7GSzgbCRKLUyMmysg/DWGpSK/v1GegiXC7slfhUutW2k3ljfv69XF7JDsLM/o1CR3KcybVB1rr/ulO8k0ipXgdtrwOBq/R+6ulBsyAIccFBcw5vcaap6+sROq9MXjWkQtTYCAAA=");
    }
    
    private static Map<String, Map<String, String>> _makeInputMap(String compressedLogData) {
    	Map<String, String> submap = new HashMap<String, String>();
    	Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
    	
    	submap.put("data", compressedLogData);
    	map.put("awslogs", submap);
    	
    	return map;
    }

    private Context _createContext() {
        TestContext ctx = new TestContext();

        ctx.setFunctionName("LogMessager");

        return ctx;
    }

    @Test
    public void testLambdaFunctionHandler() {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();
        Context ctx = this._createContext();

        handler.handleRequest(LambdaFunctionHandlerTest.errorInput, ctx);
        handler.handleRequest(LambdaFunctionHandlerTest.warnInput, ctx);
        handler.handleRequest(LambdaFunctionHandlerTest.infoInput, ctx);
    }
}