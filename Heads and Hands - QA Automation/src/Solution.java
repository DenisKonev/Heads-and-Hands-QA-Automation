import java.util.*;
import java.util.stream.IntStream;

//Решение разделено на три класса: Solution, Input и ArrayProcessor.
//Solution - содержит точку входа и отвечает за вызов методов для получения пользовательского ввода и операций над массивом
//Input - отвечает за пользовательский ввод. Просим ввести натуральное число и возвращаем его. Если не получилось распарсить ввод в int, то возвращаем 0
//ArrayProcessor - отвечает за работу с массивом. Создает HashSet уникальных размеров вложенных массивов, наполняет двумерный массив случайными значениями и сортирует значения массива согласно условиям задачи
public class Solution {
    static int intInput;
    static ArrayList<ArrayList<Integer>> arrayList2D;

    public static void main(String[] args) {
        intInput = Input.getInput();

        if (intInput > 0) {
            arrayList2D = ArrayProcessor.generateRandom2DArray(intInput);
            printArray();
        }
        else
            System.out.println("Неверный ввод");
    }

    private static void printArray() {
        for (ArrayList<Integer> rowElement : arrayList2D) {
            for (Integer columnElement : rowElement)
                System.out.format("%d ", columnElement);
            System.out.println();
        }
    }
}

class Input {
    private static final Scanner scanner;
    static {
        scanner = new Scanner(System.in);
    }

    public static int getInput() {
    int intInput;

    /*Пытаемся парсить пользовательский ввод в int, если не получилось возвращаем 0.
     *Однако, стоит заметить что, int достаточно большая переменная для этих целей, так как число больше 1_000 уже вызывает определенные проблемы с производительностью,
     * Возможно, стоит предупреждать об этом пользователя, либо отсекать слишком большие значения и выводить соответствующее сообщение, но в условия задачи про это ничего нет, поэтому оставляю как есть.
     */
    System.out.println("Введите натуральное число (целое число больше нуля) не больше определенного значения");
    try {
        intInput = scanner.nextInt();
    }
    /*Тут можно получить три разных Exception - InputMismatchException, NoSuchElementException, IllegalStateException.
     *Можно было придумать свою логику обработку для каждого из исключений, но я решил объединить все в один обработчик, чтобы не усложнять решение.
     */
    catch (Exception e) {
        intInput = 0;
    }
    finally {
        scanner.close();
    }
        return Math.max(intInput, 0);
    }
}

class ArrayProcessor {
    private static final Random random;
    static {
        random = new Random();
    }

    public static ArrayList<ArrayList<Integer>> generateRandom2DArray(final int intInput) {

        //Создаем массив массивов размер, которого соответствует числу введенного пользователем.
        ArrayList<ArrayList<Integer>> arrayList2D = new ArrayList<> (intInput);
        HashSet<Integer> uniqueSizesHashSet = new HashSet<>();

        /*Создаем HashSet уникальных размеров вложенных массивов. По условиям задачи размеры массивов не должны совпадать, поэтому и используем Set.
         *Далее заполняем массив случайными значениями. Для простоты чтения вывода я использую диапазон значений от 0 до 9 включительно.
         *Затем сортируем массив по следующей логике: массивы с четным порядковым номером отсортировать по возрастанию, с нечетным порядковым номером - по убыванию.
         */
        generateUniqueSizesHashSet(intInput, uniqueSizesHashSet);
        populate2DArrayList(intInput, arrayList2D, uniqueSizesHashSet);
        sort2DArrayList(arrayList2D);

        return arrayList2D;
    }
    private static void generateUniqueSizesHashSet(final int intInput, final HashSet<Integer> uniqueSizesHashSet) {
        while (uniqueSizesHashSet.size() != intInput)
            uniqueSizesHashSet.add(1 + random.nextInt(intInput * 5));
    }
    private static void populate2DArrayList(final int intInput, final ArrayList<ArrayList<Integer>> arrayList2D, final HashSet<Integer> uniqueSizesHashSet) {

        //Параметр bound в методе nextInt() задает диапазон значений массива от 0 до 9 включительно.
        for (Integer arraySize : uniqueSizesHashSet) {
            int[]  randomIntsArray = IntStream.generate(() -> random.nextInt(10)).limit(arraySize).toArray();
            ArrayList<Integer> randomIntsArrayList = new ArrayList<>(Arrays.stream(randomIntsArray).boxed().toList());
            arrayList2D.add(randomIntsArrayList);
        }
    }
    private static void sort2DArrayList(final ArrayList<ArrayList<Integer>> arrayList2D) {
        for (int i = 0; i < arrayList2D.size(); i++)
            if (i % 2 == 0)
                Collections.sort(arrayList2D.get(i));
            else
                Collections.sort(arrayList2D.get(i),Collections.reverseOrder());
    }
}