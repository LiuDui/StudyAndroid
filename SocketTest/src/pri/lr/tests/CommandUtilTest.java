package pri.lr.tests;

import pri.lr.Utils.CommandUtil;

public class CommandUtilTest {

    public static void main(String[] args) {
        System.out.println(CommandUtil.prepareCommand()
        .setMethod(CommandUtil.METHOD_GET)
        .setMod(CommandUtil.MOD_STR)
        .setFileName("src/data_client/one.txt").create());

        String command = CommandUtil.prepareCommand()
                .setMethod(CommandUtil.METHOD_GET)
                .setMod(CommandUtil.MOD_FILE)
                .setFileName("src/data_client/one.txt").create();

        System.out.println(command);

        CommandUtil commandUtil = CommandUtil.parseCommand(command.replace("\n", ""));
        System.out.println(commandUtil.isFile());
    }
}
