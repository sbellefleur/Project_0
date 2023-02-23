//Sarah Bellefleur
//Angstadt
//CS 256
//02-05-23

import java.util.Arrays;
import java.util.Scanner;
import gnu.getopt.Getopt;
import gnu.getopt.LongOpt;

//got help from norah

// Example of using Getopt to process command line arguments and options
public class Main {
        public static void main(String[] args) {
            // store the mode for running this program
            String mode = "";
            // we set verbose to false so it only is added when the verbose flag is passed
            boolean verbose = false;

            // specify the options that we want to support in this program
            LongOpt[] longOptions = {
                    new LongOpt("help", LongOpt.NO_ARGUMENT, null, 'h'),
                    new LongOpt("mode", LongOpt.REQUIRED_ARGUMENT, null, 'm'),
                    new LongOpt("verbose", LongOpt.NO_ARGUMENT, null, 'v')
            };
            // make a Getopt object to process the args variable
            //constructor: name, the args array, "short option string"
            // -list all the short options (last arg to the LongOpt constructor
            // - int the same order as the array
            // - for any LongOpt with a REQUIRED_ARGUMENT, put a ':' after this letter
            //long options array
            Getopt g = new Getopt("Project_0", args, "hm:v", longOptions);
            //enable error output
            g.setOpterr(true);

            //Getopt will give us one command line option at a time.
            //keep track of the current option we are processing (Getopt give this to us as
            // an int, but it's technically the character of the short option

            int choice;

            //g.getopt() returns the char/int short flag
            // or -1 if there are no more to process
            while ((choice = g.getopt()) != -1) {
                // choice contains a single command line option for us to process
                // a switch allows us to specify code to execute for various values of
                // a variable
                switch (choice) {
                    case 'v':
                        verbose = true;
                        break;
                    case 'h':
                        printHelp();
                        System.exit(0);
                        break;
                    case 'm':
                        mode = g.getOptarg();
                        // error check to ensure a valid mode
                        if (!mode.equals("average") && !mode.equals("median")) {
                            System.err.println("Error: invalid mode" + mode);
                            System.exit(1);
                        }
                        break;
                    default:
                        System.err.println("Error: invalid option");
                        System.exit(1);
                        break;
                }//switch
            }//while
            // Processing command line arguments is done.
            // TO-DO check that the mode is actually set
            if (mode.equals("")){
                //mode never got set
                System.err.println("Mode is required");
                System.exit(1);
            }

            //Create a scanner to read in our data from standard input
            Scanner in = new Scanner(System.in);

            // read an integer representing the count of values
            int count = in.nextInt();

            // create an array to store all of our data
            double[] data = new double[count];

            //read in the data and make sure that there are not more values than the count given
            // if there are, give an IndexOutOfBoundsException
            int i = 0;
            while (in.hasNextDouble()) {
                try {
                    data[i] = in.nextDouble();
                    i++;
                } catch (IndexOutOfBoundsException e) {
                    System.err.println("Data incompatible: too many values for count given");
                    System.exit(1);
                    return;
                }
            }
            //check to see if our data set is empty
            if (count == 0){
                System.out.println("No data => no statistics!");
                System.exit(0);
            }
            //check to see if average was passed as an argument
            if (mode.equals("average")){
                System.out.printf("Average: %.2f" , math_average(count, data, verbose));
            }
            //check to see if median was passed as an argument
            if (mode.equals("median")){
                System.out.printf("Median: %.1f" , math_median(count, data, verbose));
            }

            // we should have read all of our data
            // FIXME HINT: we might need to do a little error checking (in.hasNextDouble()-->boolean)

        }//main

        private static void printHelp() {
            System.out.println("Usage: java [options] Main [-m average|median] [-h]");
            System.out.println("This program is an example of processing command line");
            System.out.println("arguments with Getopt.");
        }
        //calculate the average of a set of data
        public static double math_average(int count, double[] data, boolean verbose){
            double avg = 0;
            for (int i = 0; i < count; i++)
                avg += data[i];
            if (verbose){
                print_verbose(count);
            }
            return avg/count;
        }//average
        //if the verbose flag is passed, print out the number of values read
        public static void print_verbose(int count){
            System.out.println("Reading "+ count + " numbers.");
            System.out.println("Read "+ count + " numbers.");
        }//verbose
        //calculate the median of a set of data
        public static double math_median(int count, double[] data, boolean verbose){
            if (verbose) {
                print_verbose(count);
            }

            // TODO sort array
            Arrays.sort(data);

            if (count % 2 == 0){
                return (((data[count/2])+(data[(count/2)-1]))/2.0);
            }
            else{
                return (data[count/2]);
            }
        }//median
    }//class


