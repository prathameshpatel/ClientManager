package io.github.prathameshpatel.clientmanager.db;

import io.github.prathameshpatel.clientmanager.entity.Client;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Prathamesh Patel on 12/16/2017.
 */

public class DataGenerator {

    public static List<Client> generateClients() {
        List<Client> clientList = new ArrayList<>();
        Client client1 = new Client("Prathamesh","Patel","Goregaon, MH, IN","7816521748");
        Client client2 = new Client("Manish","Mourya","Andheri, MH, IN","3465872312");
        Client client3 = new Client("Rudresh","Trivedi","Vadodra, GJ, IN","4567823908");
        Client client4 = new Client("Vaibhav","Mhatre","Borivli, MH, IN","6579803452");
        Client client5 = new Client("Manuprasad","Kela","","5638479332");
        Client client6 = new Client("Ajith","Joseph","","5639635489");
        Client client7 = new Client("Tejas","Rane","Thane, MH, IN","0494524539");
        Client client8 = new Client("Pratiksha","More","Malad, MH, IN","9462495764");
        Client client9 = new Client("Keerthana","Bekal","Bangalore, KN, IN","0475385966");
        Client client10 = new Client("Snehal","Singhi","Borivli, MH, IN","4331316341");
        Client client11 = new Client("Dishant","Shah","Goregaon, MH, IN","4231850143");
        Client client12 = new Client("Kishore","Sundar","Kandivli, MH, IN","9624181327");

        clientList.add(client1);
        clientList.add(client2);
        clientList.add(client3);
        clientList.add(client4);
        clientList.add(client5);
        clientList.add(client6);
        clientList.add(client7);
        clientList.add(client8);
        clientList.add(client9);
        clientList.add(client10);
        clientList.add(client11);
        clientList.add(client12);

        return clientList;
    }
}
