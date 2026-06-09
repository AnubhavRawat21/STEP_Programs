package CompanyOrder;

public class TestMain       
{
    public static void main(String args[])
    {
        var company=new Company("Shopperstop");
        var ghost=new Company("Shopperstop");
        company.setName("Shopperstop");
        ghost.setName("Ghost");
        System.out.println(company.getName());
        System.out.println(ghost.getName());

        //Company decided to purchase items to sell int their stores

        var shirt = new Item(1,"Allen Solly",2000);
        var pant = new Item(2,"Levis",3000);
        var shoes = new Item(3,"Nike",5000);

        //We first added what are the items availble in the company to sell
        company.addItem(shirt);
        company.addItem(pant);
        company.addItem(shoes);

        System.out.println(" Items available in the company");
        for(Item item:company.getItems())
        {
            System.out.println(item.getTitle()+" - INR "+item.getRate()+" - "+item.getItemNumber());
        }

        //Regular customer walked into the store, so we make object of customer class
        var regularCustomer = new Customer("Siddhant","1");
        //want to buy 2 pants and 1 shirt, the customer finally chose these two items,
        //So we make 
        var orderItem1 = new OrderItem(pant,2);
        var orderItem2= new OrderItem(shirt,1);

        //now customer goes to billing counter 
        var regularOrder = new Order();

        regularOrder.setCustomer(regularCustomer);

        regularOrder.addOrderItem(orderItem1);
        regularOrder.addOrderItem(orderItem2);

        //regular cusotmer makes the payment and order handed over to customer

        regularCustomer.addOrder(regularOrder);

        company.addCustomer(regularCustomer);

        //-----------------------
        //Registered customer walked into the store

        var registeredCustomer= new RegisteredCustomer("Siddhant","1",10.0);
        var orderItem3 = new OrderItem(pant,2);
        var orderItem4 = new OrderItem(shoes,2);

        var registeredOrder= new Order();

        registeredOrder.setCustomer(registeredCustomer);

        registeredOrder.addOrderItem(orderItem3);
        registeredOrder.addOrderItem(orderItem4);
        
        registeredCustomer.addOrder(registeredOrder);
        company.addCustomer(registeredCustomer);

        System.out.println(company.getTotalOrderValue());

        // System.out.println(company.getCustomers().get(0).getName()+" has bought "+company.getTotalOrderValue());
        // System.out.println(company.getCustomers().get(1).getName()+" has bought "+company.getTotalOrderValue());


        // var item = new Item()

    }
}
/*
STEPS
Create a console application / java project
Create company class and copy and paste the code given
Create Item class and copy and paste code given
Create Customer Class - No code just create
Create Registered Customer class extending Customer - No code just the class creation
*/

/*


 1. Naming conventions - Should be meaningful names, easily understandable

 2. Casing - Camel Casing - First letter is always small, every word starts with Capital

 if its a combination of more than one word - simpleInterest;

 Class Names - Always Capitals to be begin with 

 Method Names - CamelCasing - Verbs - calculateSimpleInterest()

 Class Variables - camel casing

 Code organisation- Structure and flow of your code

 Code Identation and Formatting - Look neat and clear. There should be
June 9
Company
Customer
RegisteredCustomer
Item
Order

Company Has-A realtionship with Item ->Done
Customer Is-A relationship with Registered Customer ->Done
Customer Has a relationship with Order ~ bidirectional ~ multiplicity ->Done



Classes, Objects Getters and setters
Interfaces
Collections - List and Map
Design Principles and Patterns - SOLID, Structural Design Patterns
Configuration Based Programming
Unit Testable Code
Test Main Code - This is very important for you to know how totest your code

~ when implementing bidirectional relationship, let say there could be multiple B 
object of class A then make a list of B class objects in class A and as it is bidirectional 
we have to make an object of class A in class B too.


We need to know total order value of the company
CEO has asked to find total orde value of the company


*/