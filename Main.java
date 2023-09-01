import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static class Pokemon implements Comparable<Pokemon> {
        String name;
        String type;
        int power;

        Pokemon(String name, String type, int power) {
            this.name = name;
            this.type = type;
            this.power = power;
        }

        @Override
        public String toString() {
            return String.format("%s(%d)", this.name, this.power);
        }

        @Override
        public int compareTo(Pokemon p) {
            int result = this.name.compareTo(p.name);
            if (result == 0) {
                result = Integer.compare(p.power, this.power);
            }
            return result;
        }
    }

    private static ArrayList<Pokemon> pokemons = new ArrayList<>();
    private static HashMap<String, TreeSet<Pokemon>> pokemonsByType = new HashMap<>();

    private static StringBuilder result = new StringBuilder();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String[] command = scanner.nextLine().split(" ");
            switch (command[0]) {
                case "add":
                    addPokemon(command);
                    break;
                case "find":
                    findPokemon(command);
                    break;
                case "ranklist":
                    rankList(command);
                    break;
                default:
                    System.out.println(result);
                    return;
            }
        }
    }

    private static void addPokemon(String[] command) {
        Pokemon p = new Pokemon(command[1], command[2], Integer.parseInt(command[3]));
        int position = Integer.parseInt(command[4]);

        pokemons.add(position - 1, p);

        if (!pokemonsByType.containsKey(command[2])) {
            pokemonsByType.put(command[2], new TreeSet<>());
        }

        pokemonsByType.get(command[2]).add(p);

        result.append(
                String.format("Added pokemon %s to position %d\n",
                        command[1],
                        Integer.parseInt(command[4])));
    }

    private static void findPokemon(String[] command) {
        result.append(String.format("Type %s: ", command[1]));

        if (pokemonsByType.containsKey(command[1])) {
            List<String> resultPokemons = pokemonsByType.get(command[1]).stream()
                    .limit(5)
                    .map(Pokemon::toString)
                    .collect(Collectors.toList());

            result.append(String.join("; ", resultPokemons));
        }

        result.append("\n");
    }

    private static void rankList(String[] command) {
        int start = Integer.parseInt(command[1]) - 1;
        int end = Integer.parseInt(command[2]) - 1;

        List<String> rankedPokemons = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            rankedPokemons.add(String.format("%d. %s", i + 1, pokemons.get(i)));
        }

        result.append(String.join("; ", rankedPokemons));
        result.append("\n");
    }
}