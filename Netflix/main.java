package Netflix;
class Main {

    public static void main(String[] args) {

        Catalog catalog = new Catalog();

        Content c1 =
                new Content(
                        "N101",
                        "Stranger Things",
                        "Sci-Fi",
                        55
                );

        Content c2 =
                new Content(
                        "N102",
                        "Money Heist",
                        "Crime",
                        48
                );

        catalog.addContent(c1);
        catalog.addContent(c2);

        System.out.println("All Content:");
        catalog.displayAll();

        System.out.println("\nSearch:");

        Content result =
                catalog.searchByTitle("Money Heist");

        if (result != null) {

            System.out.println(
                    result.getTitle()
                    + " Found"
            );
        }

        catalog.removeContentById("N101");

        System.out.println("\nAfter Removal:");
        catalog.displayAll();
    }
}