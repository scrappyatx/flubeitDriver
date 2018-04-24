/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.demoBatchCreation;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created on 4/23/2018
 * Project : Driver
 */
public class BatchRandomTitleGenerator {
    private static ArrayList<String> batchTitleList;

    public BatchRandomTitleGenerator(){

    }

    public static String getRandomTitle(){
        createBatchTitleArray();
        Random random = new Random();
        return batchTitleList.get(random.nextInt(batchTitleList.size()));
    }

    private static void createBatchTitleArray(){
        batchTitleList = new ArrayList<String>();

        batchTitleList.add("The Terror in the Rhine");
        batchTitleList.add("The Purrfect Bachelor");
        batchTitleList.add("Threshold of Illusion");
        batchTitleList.add("A Clue in the Country");
        batchTitleList.add("Telltale Pattern");
        batchTitleList.add("Enchantment in June");
        batchTitleList.add("Appleby and the Playboy");
        batchTitleList.add("A Minister's Romance");
        batchTitleList.add("A Human Kept");
        batchTitleList.add("A Gallant Pursuit");
        batchTitleList.add("The Apple of Illusion");
        batchTitleList.add("Thorn of Alpha");
        batchTitleList.add("The Essential Killing");
        batchTitleList.add("Banker's Vineyard");
        batchTitleList.add("The Wayward Jeopardy");
        batchTitleList.add("A Dog's Duty");
        batchTitleList.add("The Hard Unchained");
        batchTitleList.add("The Bodyguard's Sword");
        batchTitleList.add("The Bride's Bodyguard");
        batchTitleList.add("A Trio for Lute");
        batchTitleList.add("Master and Honesty");
        batchTitleList.add("Alibi for a Kidnapping");
        batchTitleList.add("The Legendary Road");
        batchTitleList.add("The Officer's Pass");
        batchTitleList.add("A Lover Affair");
        batchTitleList.add("A Sliver of Evidence");
        batchTitleList.add("Uncharted Guns");
        batchTitleList.add("A Rhythm Tempest");
        batchTitleList.add("An Unconventional Presumption");
        batchTitleList.add("The Gift of the Angel");
        batchTitleList.add("Infinite Companions");
        batchTitleList.add("Foul Showers");
        batchTitleList.add("The Clouds of Abilene");
        batchTitleList.add("Taste of Innocence");
        batchTitleList.add("Lucifer and the Duke");
        batchTitleList.add("Black Reluctant");
        batchTitleList.add("Empty Remains");
        batchTitleList.add("Dead for a Gypsy");
        batchTitleList.add("Sins of Rage");
        batchTitleList.add("The Twelve Raiders");
        batchTitleList.add("Giant Names");
        batchTitleList.add("Hallowed Desert");
        batchTitleList.add("A Holiday Business");
        batchTitleList.add("Accidental Alliances");
        batchTitleList.add("A Villain's Ascension");
        batchTitleList.add("Tongue in Silver");
        batchTitleList.add("Rage of Dawn");
        batchTitleList.add("Dead for a Dealer");
        batchTitleList.add("Menace in the Kitchen");
        batchTitleList.add("Imperial Windows");
        batchTitleList.add("Absolute Response");
        batchTitleList.add("Cavanaugh on Horseback");
        batchTitleList.add("The Salt Spies");
        batchTitleList.add("Fugitive's Breakout");
        batchTitleList.add("Covenant of the Pack");
        batchTitleList.add("A Single's Claim");
        batchTitleList.add("Metal Foe");
        batchTitleList.add("Bungalow Charity");
        batchTitleList.add("Maigret and the Lawman");
        batchTitleList.add("Blood of Rocks");
        batchTitleList.add("The Depth Target");
        batchTitleList.add("Afterglow and Day");
        batchTitleList.add("Saint's Fortune");
        batchTitleList.add("The Mulberry Hex");
        batchTitleList.add("The Sheik's Hotel");
        batchTitleList.add("Tarzan Reckless");
        batchTitleList.add("The Termination Barrier");
        batchTitleList.add("Dust on the Massacre");
        batchTitleList.add("The Brotherhood of the Unicorn");
        batchTitleList.add("The Hero Cake");
        batchTitleList.add("Watch and Deception");
        batchTitleList.add("Days of Anger");
        batchTitleList.add("Changer of Gods");
        batchTitleList.add("Fang and Ash");
        batchTitleList.add("Jack the Champion");
        batchTitleList.add("Gentlemen of the Sun");
        batchTitleList.add("Pillow of the Town");
        batchTitleList.add("Mayhem in the Afterglow");
        batchTitleList.add("Shadows of the Virgin");
        batchTitleList.add("Rebel by Consent");
        batchTitleList.add("A Marriage of Evil");
        batchTitleList.add("Marooned in Blue");
        batchTitleList.add("The Rapture of the Army");
        batchTitleList.add("Hallowed Desert");
        batchTitleList.add("Hero by Command");
        batchTitleList.add("The Maverick's Nose");
        batchTitleList.add("Little Pregnant");
        batchTitleList.add("The Bear's Mistake");
        batchTitleList.add("Painted Detectives");
        batchTitleList.add("Reality Treat");
        batchTitleList.add("Merlin's Prayer");
        batchTitleList.add("Screen Bodies");
        batchTitleList.add("The Escape Cord");
        batchTitleList.add("The Utopia Boss");
        batchTitleList.add("A Reunion Wife");
        batchTitleList.add("An Atomic Thief");
        batchTitleList.add("The Bloody Nabob");
        batchTitleList.add("The Currents of Cthulhu");
        batchTitleList.add("Terms of Prey");
        batchTitleList.add("The Sword of Guilt");
        batchTitleList.add("Confidential Swarm");
        batchTitleList.add("The Lion's Man");
        batchTitleList.add("The Cats of Evangeline");
        batchTitleList.add("Canary Crusade");
        batchTitleList.add("Tarzan the Tiger");
        batchTitleList.add("Caroline and the Mayor");
        batchTitleList.add("Serpent's Anthem");
        batchTitleList.add("A Familiar Guardian");
        batchTitleList.add("The Face of the Century");
        batchTitleList.add("The Legion of Mankind");
        batchTitleList.add("The Body in the Morning");
        batchTitleList.add("Crown of Masks");
        batchTitleList.add("The Jewels of Summer");
        batchTitleList.add("Widow for Beauty");
        batchTitleList.add("An Indomitable Ruse");
        batchTitleList.add("The Phoenix and the Playboy");
        batchTitleList.add("Vine of the Betrayer");
        batchTitleList.add("Kingmaker's Bane");
        batchTitleList.add("Prisoner on the Ladder");
        batchTitleList.add("The Oracle Heroes");
        batchTitleList.add("The Child's Violin");
        batchTitleList.add("Rx for Blackshirt");
        batchTitleList.add("Thug Peak");
        batchTitleList.add("Tides of Dune");
        batchTitleList.add("The Old Gods");
        batchTitleList.add("The Ragged Brother");
        batchTitleList.add("The Comet Feathers");
        batchTitleList.add("The Book of the Law");
        batchTitleList.add("Imperial H");
        batchTitleList.add("Santa's Labyrinth");
        batchTitleList.add("The Babe and the Miss");
        batchTitleList.add("Illusions of Orion");
        batchTitleList.add("A Witch's Pursuit");
        batchTitleList.add("A Candle in the Storm");
        batchTitleList.add("Wedding Heirs");
        batchTitleList.add("Dance for a Blonde");
        batchTitleList.add("Gravity Daughters");
        batchTitleList.add("Terms of Isis");
        batchTitleList.add("The Evil Train");
        batchTitleList.add("Whisper in the Limelight");
        batchTitleList.add("Brush with Love");
        batchTitleList.add("A Cosmic Waltz");
        batchTitleList.add("Radiant Treasures");
        batchTitleList.add("Sources of the Tower");

    }
}
