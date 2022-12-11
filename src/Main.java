import javax.print.attribute.standard.PrinterMakeAndModel;
import java.util.Random;

public class Main {
    public static int bossHealth = 2000;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int hlIndex;
    public static int plus_heal = 120;
    public static int[] heroesHealth = {270, 260, 250, 200, 400, 200, 240, 200};
    public static int[] heroesDamage = {20, 15, 10, 0, 13, 10, 20, 20};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int roundNumber;
    public static String criticalMessage;
    public static boolean ranLucky;
    public static String messageLucky = ">>> Lucky got really lucky";
    public static boolean rstn;


    public static void main(String[] args) {
        printStatistics();

        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();
        ranStun();
        bossHits();
        heroesHitAndHealing();
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randIndex];
    }

    public static boolean ranStun() {
        Random ranStun = new Random();
        rstn = ranStun.nextBoolean();
        return rstn;
    }
    public static void bossHits() {
        Random randomL = new Random();
        ranLucky = randomL.nextBoolean();

            for (int i = 0; i < heroesHealth.length; i++) {
                if (rstn == true && roundNumber > 1) break;
                if (heroesHealth[i] > 0) {
                    if (heroesHealth[i] - bossDamage < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        if (heroesHealth[4] > 0) {
                            if (heroesHealth[i] == heroesHealth[5] && ranLucky == true) {
                                continue;
                            }
                            heroesHealth[i] = heroesHealth[i] - (bossDamage - 10);
                            heroesHealth[4] = heroesHealth[4] - 10;//golem - 50 "урон от босса" - 40 "1/5 часть урона по другим игрокам"
                            if (heroesHealth[i] == heroesHealth[6]) {
                                if (heroesDamage[6] <= 70) {
                                    heroesDamage[6] += bossDamage;
                                    if (heroesDamage[6] > 70) {
                                        heroesDamage[6] -= bossDamage;
                                    }
                                }
                            }

                        } else {
                            heroesHealth[i] = heroesHealth[i] - bossDamage;
                        }
                    }


                }
            }
    }
    public static void heroesHitAndHealing() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; //2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    criticalMessage = "Critical damage: " + damage;
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
        //Medic прибавливает рандомному игроку +120
        do {
        Random healing = new Random();
        hlIndex = healing.nextInt(heroesHealth.length);} while(hlIndex == 3 && hlIndex == 5);
        if (heroesHealth[3] > 0) {
                if (heroesHealth[hlIndex] != 0){
                    if (roundNumber > 1){
                        heroesHealth[hlIndex] += plus_heal;// медик лечит если даже здоровье больше 100, медик недоживает пока у кого то будет здоровье менее 100
                    }
                }
        }
    }
    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ------------");

        System.out.println("|| Boss health: " + bossHealth + " || damage: " + bossDamage + " || defence: " +
                (bossDefence == null ? "No defence" : bossDefence));

        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] == 0) {
                System.out.println(heroesAttackType[i] + " is dead((");
            } else {
                System.out.println(heroesAttackType[i] + " || health: " + heroesHealth[i] + " || damage: " + heroesDamage[i]);
            }
        }
        if (criticalMessage != null) {
            System.out.println(">>> " + criticalMessage);
        }
        if (criticalMessage != null) {
            if (heroesHealth[3] > 0){
            System.out.println(">>> Medic has healed " + heroesAttackType[hlIndex]);
            }else {
                System.out.println(">>> Medic cannot heal, Medic is dead");
            }
            if(ranLucky == true && heroesHealth[5] > 0 ) {
                System.out.println(messageLucky);
            }
            if (rstn == true && roundNumber > 1) {
                System.out.println(">>> Boss did not attacked, Boss stunned by Thor!!!");
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }
}