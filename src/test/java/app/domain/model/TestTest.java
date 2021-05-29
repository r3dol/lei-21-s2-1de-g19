package app.domain.model;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestTest {

    @Test
    public void checknhsCodeRulesValid() {

        app.domain.model.Test test = new app.domain.model.Test();

        String resultNhsCode = "123456789098";
        test.checknhsCodeRules(resultNhsCode);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void checknhsCodeRulesInvalidBlank() {

        app.domain.model.Test test = new app.domain.model.Test();

        String resultNhsCode = "";
        test.checknhsCodeRules(resultNhsCode);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void checknhsCodeRulesInvalidLength() {

        app.domain.model.Test test = new app.domain.model.Test();

        String resultNhsCode = "1234";
        test.checknhsCodeRules(resultNhsCode);
    }

    @org.junit.Test(expected = IllegalArgumentException.class)
    public void checknhsCodeRulesInvalidLettersNumbers() {

        app.domain.model.Test test = new app.domain.model.Test();

        String resultNhsCode = "123456-890uh";
        test.checknhsCodeRules(resultNhsCode);
    }

    @Test
    public void generateCode() {


        Company company = new Company("Many Labs");

        ParameterCategory parameterCategory = new ParameterCategory("a", "12345");
        List <ParameterCategory> pcList = new ArrayList<>();
        pcList.add(new ParameterCategory("test","test0"));
        TestType testType = new TestType("34567","assdf","swab", pcList, new ExternalModuleBloodWithoutKey());

        List<Parameter> parameters = new ArrayList<>();
        Parameter parameter = new Parameter("45678","asdfg","yujhk",parameterCategory);
        parameters.add(parameter);


        int codeLengthResult = 12;

        app.domain.model.Test teste = company.getTestStore().createTest(testType,parameters,pcList, "123456789098", "121212121212");
        teste.generateCode();
        int codeLengthExpected = teste.getCode().length();

        assertEquals(codeLengthResult,codeLengthExpected);
    }
}