package CinnamoRoll;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

class Parser {

    private final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    String[] parse(String str) {
        String[] output = str.split(" ", 2);
        output[0] = output[0].toUpperCase();
        return output;
    }
    /**
     * Parses a string array representing a task and creates the corresponding Task object.
     * Supported task types are "TODO," "DEADLINE," and "EVENT."
     * For "TODO," expects the task description.
     * For "DEADLINE," expects the task description and deadline in the format "yyyy-MM-dd HH:mm."
     * For "EVENT," expects the task description and event schedule in the format "yyyy-MM-dd HH:mm /from yyyy-MM-dd HH:mm /to yyyy-MM-dd HH:mm."
     *
     * @param arr The string array representing the task.
     * @return A Task object based on the parsed input.
     * @throws CinnamoTimeException If there is an error parsing the date and time information.
     */
    Task parse_tasks(String[] arr) throws CinnamoTimeException {
        try {
            String identifier = arr[0];
            if (identifier.equals("TODO")) {
                return new Todos(arr[1]);
            } else if (identifier.equals("DEADLINE")) {
                String[] schedule = arr[1].split(" /by ", 2);
                return new Deadlines(schedule[0], LocalDateTime.parse(schedule[1], this.format));
            } else {
                String[] schedule = arr[1].split(" /from | /to ");
                return new Events(schedule[0], LocalDateTime.parse(schedule[1], this.format),
                        LocalDateTime.parse(schedule[2], this.format));
            }
        } catch (DateTimeParseException dtp) {
            throw new CinnamoTimeException();
        }
    }
}