import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Market {
    public static void tradingHall() throws IOException, InterruptedException {
        Main.scene = "Market";
        Main.screen.clear();
        Main.screen.refresh();
        Main.formatter.printMulti(6, List.of("Plots of land funded:"), TextFormatter.PaddingAlignment.CENTER, TextFormatter.BorderStyle.DOUBLE, 0);
        
        boolean inMarket = true;
        while (inMarket) {
            List<String> landDisplay = new ArrayList<>();
            List<String> landDisplayLegend = new ArrayList<>();
            String line;
            for (int i = 0; i < 100; i+=10) {
                line = "";
                for (int j = 0; j < 10; j++) {
                    if (i+j<Globals.plantationSize) {
                        line += "▓▓";
                    } else {
                        line += "░░";
                    }
                }
                landDisplay.add(line);
            }
            landDisplayLegend.add("One unit of land = 100 hectares");
            landDisplayLegend.add("└─[▓▓] Land funded");
            landDisplayLegend.add("└─[░░] Uncultivated land");
            landDisplayLegend.add("");
            landDisplayLegend.add("For every 50 hectares of land you fund, you produce one unit of liquorice.");
            landDisplayLegend.add("Liquorice production can lower the chance of a Nemesis attack, and as such lower market prices.");
            
            Main.formatter.printMulti(10, Main.formatter.createBox(1, 1, landDisplay, TextFormatter.BorderStyle.NOTHING), TextFormatter.PaddingAlignment.CENTER);
            Main.formatter.printMulti(23, Main.formatter.createBox(1, 1, landDisplayLegend, TextFormatter.BorderStyle.NOTHING), TextFormatter.PaddingAlignment.CENTER);
            Main.formatter.printMulti(32, Main.formatter.createBox(1, 1, List.of("Money: $"+Globals.money, "Chance of encountering Nemesis: "+Globals.nemesisPercentage), TextFormatter.BorderStyle.DOUBLE), TextFormatter.PaddingAlignment.CENTER);
            Main.formatter.printMulti(37, Main.formatter.createBox(22, 1, List.of(), TextFormatter.BorderStyle.DOUBLE), TextFormatter.PaddingAlignment.CENTER);
            // switch (Main.formatter.printSelection(32, List.of("Purchase land [$100]", "Ensnare land [$20]", "Exit"), List.of("buy", "trap", "quit"), TextFormatter.PaddingAlignment.CENTER)) {
            switch (Main.formatter.printSelection(38, List.of("Purchase land [$100]", " [Exit] "), List.of("buy", "quit"), TextFormatter.PaddingAlignment.CENTER)) {
                case "buy" -> {
                    if (Globals.plantationSize>=100) {
                        Main.formatter.alert(42, List.of(
                            "Due to the third act of the National Laws for Liquorice production,",
                            "no individual is allowed to fund"
                        ));
                    } else if (Globals.money>=100) {
                        Main.formatter.alert(42, List.of(
                            "You bought 100 hectares of land,",
                            "capable of producing 1 unit of Liquorice daily."
                        ));
                        Globals.plantationSize += 1;
                        Globals.money -= 100;
                    } else {
                        Main.formatter.alert(42, List.of(
                            "$"+Globals.money+" just won't cut it, officer."
                        ));
                    }
                }
                // case "trap" -> {
                //     if (Globals.money>=20) {
                //         Main.formatter.alert(42, List.of(
                //             "You applied security measures on one plot of the lands you fund.",
                //             "Gain 1 intel the next time you engage in combat."
                //         ));
                //         Globals.plantationSize += 2;
                //         Globals.money -= 20;
                //     } else {
                //         Main.formatter.alert(42, List.of(
                //             "$"+Globals.money+" just won't cut it, officer."
                //         ));
                //     }
                // }
                case "quit" -> {
                    inMarket = false;
                    Saves.saveSave(Saves.currentFile, "Campaign selector", Main.characterName, Globals.nemesisPercentage, Globals.money, Globals.progress, Globals.plantationSize);
                    SceneController.loadScene("Campaign selector");
                }
            }
        }
    }
}
