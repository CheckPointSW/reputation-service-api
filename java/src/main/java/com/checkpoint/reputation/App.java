package com.checkpoint.reputation;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        AppArguments arguments = new AppArguments(args);
        new ReputationClient(arguments).run();
    }

}
