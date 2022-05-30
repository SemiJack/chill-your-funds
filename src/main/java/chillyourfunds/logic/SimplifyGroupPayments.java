//package chillyourfunds.logic;
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.*;
//
//public class SimplifyGroupPayments {
//    static List printBill;
//
//
//        public static void findPath(Map details) {
//            printBill = new ArrayList();
//
//            Double Max_Value = (Double) Collections.max(details.values());
//            Double Min_Value = (Double) Collections.min(details.values());
//            if (Max_Value != Min_Value) {
//                String Max_Key = getKeyFromValue(details, Max_Value).toString();
//                String Min_Key = getKeyFromValue(details, Min_Value).toString();
//                Double result = Max_Value + Min_Value;
//                result = round(result, 1);
//                if ((result >= 0.0)) {
//                    //printBill.add(Min_Key + " needs to pay " + Max_Key + ":" + round(Math.abs(Min_Value), 2));
//                    System.out.println(Min_Key + " musi zaplacic " + Max_Key + ":" + round(Math.abs(Min_Value), 2));
//
//                    details.remove(Max_Key);
//                    details.remove(Min_Key);
//                    details.put(Max_Key, result);
//                    details.put(Min_Key, 0.0);
//                } else {
//                    // printBill.add(Min_Key + " needs to pay " + Max_Key + ":" + round(Math.abs(Max_Value), 2));
//                    System.out.println(Min_Key + " musi zaplacic " + Max_Key + ":" + round(Math.abs(Max_Value), 2));
//
//
//                    details.remove(Max_Key);
//                    details.remove(Min_Key);
//                    details.put(Max_Key, 0.0);
//                    details.put(Min_Key, result);
//                }
//                findPath(details);
//            }
//
//        }
//
//
//        public static Map groupToSimplify(Group group) {
//            Map<String,Double> mapOfBalancesInGroup = new HashMap<>();
//            for(int i = 0; i < group.people.size(); i++) {
//                mapOfBalancesInGroup.put(group.people.get(i).name, (double) -(group.people.get(i).balance));
//            }
//            return mapOfBalancesInGroup;
//        }
//
//
//        public static Object getKeyFromValue(Map hm, Double value) {
//            for (Object o : hm.keySet()) {
//                if (hm.get(o).equals(value)) {
//                    return o;
//                }
//            }
//            return null;
//        }
//
//        public static double round(double value, int places) {
//            if (places < 0)
//                throw new IllegalArgumentException();
//
//            BigDecimal bd = new BigDecimal(value);
//            bd = bd.setScale(places, RoundingMode.HALF_UP);
//            return bd.doubleValue();
//        }
//
//    }
//
//TO JUZ NIEPOTRZEBNE ALE W RAZIE W MOZE ZOSTAC NARAZIE

