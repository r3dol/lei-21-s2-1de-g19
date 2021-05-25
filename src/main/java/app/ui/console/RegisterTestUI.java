package app.ui.console;

import app.controller.RegisterTestController;
import app.domain.model.Test;
import app.ui.console.utils.Utils;
import auth.domain.store.TestStore;
import auth.mappers.dto.ClientDto;
import auth.mappers.dto.ParametersDto;
import auth.mappers.dto.TestTypeDto;
import java.util.List;

/**
 * Represents the register client UI.
 *
 * @author Eduardo Gonçalves
 */
public class RegisterTestUI implements Runnable {


    /**
     * Register Test Controller.
     */
    private RegisterTestController ctrl;


    /**
     * Allows access to register test controller methods.
     */
    public RegisterTestUI (){

        ctrl = new RegisterTestController();


    }

    /**
     * It allows you to enter the data necessary to register a test associated to a registered client, select the test type and the parameter(s) to register a test, make the confirmation and see if the operation was successful or not.
     */
    public void run() {

        int option = 0;

        System.out.printf("\n--- Requested data to register a test ---");
        String citizenID = Utils.readLineFromConsole("\nType the client's citizen card numer:");

        ClientDto cl = ctrl.getClient(citizenID);

        if (cl != null) {

            boolean confirmation = Utils.confirm(String.format("Is this information right? If so type s, if not type n. \n\n Citizen card number: %s \n National Healthcare Service number (NHS): %s " +
            "\n Birth date: %s \n Sex: %s \n Tax Identification number (TIN): %s \n Phone number: %s \n email: %s \n name: %s ", cl.getCitizenID(), cl.getNhsID(), cl.getBirthDate(), cl.getSex(), cl.getTIN(), cl.getPhoneNumber(), cl.getEmail(), cl.getName()));

            if (confirmation) {

                if (ctrl.getAllTestType().isEmpty())
                    System.out.printf("\nThere aren't any test type list at the moment.\n");
                else {

                    List<TestTypeDto> listTestTypeDto = ctrl.getAllTestType();

                    Utils.showList(listTestTypeDto, "Available test types.");
                    TestTypeDto opcao = (TestTypeDto) Utils.selectsObject(listTestTypeDto);

                    if (opcao != null){
                        List<ParametersDto> listParametersDto = ctrl.getAllParametersByTestType(opcao);

                        System.out.println("Available parameters associated to the test type.");

                        int index = 0;
                        for (ParametersDto parametersDto : listParametersDto)
                        {
                            index++;

                            System.out.println(index + ". " + parametersDto.toString());
                        }

                        // ACRESCENTEI O STRING AUX = E O ARRAY
                        String aux = Utils.readLineFromConsole("Choose the parameters that you want to associate to the test type.");
                        String [] aux1 = aux.split(" ");


                        boolean confirmation1 = Utils.confirm(String.format("Are you sure this is the info of the test type ? If so type s, if not type n. \n\n Test type: %s", opcao.toString()));


                        for (int i=0; i<aux1.length; i++){
                            if (aux1[i].charAt(i) == listParametersDto.indexOf(i))
                                System.out.println(listParametersDto.indexOf(i));
                        }


                        /*
                        if (confirmation1){
                            for (ParametersDto parametersDto: listParametersDto) {

                                System.out.println(parametersDto.toString());
                            }
                        }

                         */

                        //aparecer só o(s) parameter(s) mediante a opção que escreve no teclado
                        //validação para não poder registar o mesmo teste
                        // Não deixar o programa ir abaixo quando entro para registar um teste e o client não existe


                        boolean confirmation2 = Utils.confirm("Are you sure this is the info of parameter(s) associated to the test type ?");

                        if (confirmation1 && confirmation2){

                            Test test = ctrl.createTest(listParametersDto,opcao,cl.getCitizenID());
                            ctrl.saveTest(test);
                            System.out.println("Operation was a success and the test was registered.");


                            /*VER SE O TEST ESTA NA TEST LIST
                            List<Test> x = ctrl.testStore.SeeList();
                            for (Test yy: x ) {
                                System.out.printf(yy.toString());
                            }
                             */
                        }
                        //System.out.println(test.toString());

                    } else {
                        System.out.println("Não quer selecionar nenhum test type da lista.");
                    }
                }
                
            } else {
                System.out.println("Corrigir dados");
            }

        }
    }
}