# Mad Libs

A Java program to play the game of Mad Libs.

## Description

This program allows users to create and view Mad Libs based on predefined templates. Users can input their own words to replace the placeholders in the templates and generate their customized stories.

## Usage

1. Run the program.
2. Choose whether to create a mad-lib or view a mad-lib.
3. If creating a mad-lib, provide the input file name and output file name. The program will generate the mad-lib based on the provided input file and save it to the output file.
4. If viewing a mad-lib, provide the input file name. The program will display the content of the mad-lib template.
5. Follow the prompts to enter your own words and complete the mad-lib.
6. Enjoy your customized mad-lib!

## Method Structure

- `Main method`: The entry point of the program.
- `createMadLib`: Method responsible for creating a new Mad Lib based on user input.
- `viewMadLib` : Method responsible for displaying the content of a Mad Lib template.
- `processMadLib` : Method that processes a line of the Mad Lib template and replaces placeholders with user inputted values.
- `fileOperations` : Method that handles file operations such as retrieving template files and reading/writing lines.
- `generatePrompt`: Method that generates the prompt for a placeholder based on its name.

## Mad Lib Templates

The Mad Lib templates are stored in the `MadLibTemplates` folder. You can add your own template files to this folder in order to extend the available Mad Lib options.

## Dependencies

This program does not have any external dependencies.

## License

This project is licensed under the [MIT License](LICENSE).
