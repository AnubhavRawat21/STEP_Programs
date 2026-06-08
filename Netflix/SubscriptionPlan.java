package Netflix;
abstract class SubscriptionPlan {

    protected String planName;
    protected double monthlyCost;

    public SubscriptionPlan(String planName,
                            double monthlyCost) {

        this.planName = planName;
        this.monthlyCost = monthlyCost;
    }

    public double calculateMonthlyCost() {
        return monthlyCost;
    }

    public abstract void displayFeatures();
}

class PremiumPlan extends SubscriptionPlan {

    public PremiumPlan() {
        super("Premium", 799);
    }

    @Override
    public void displayFeatures() {

        System.out.println(
            "Premium Plan"
        );

        System.out.println(
            "4K Ultra HD"
        );

        System.out.println(
            "4 Screens"
        );

        System.out.println(
            "All Devices"
        );
    }
}

class MobilePlan extends SubscriptionPlan {

    public MobilePlan() {
        super("Mobile", 199);
    }

    @Override
    public void displayFeatures() {

        System.out.println(
            "Mobile Plan"
        );

        System.out.println(
            "SD Quality"
        );

        System.out.println(
            "1 Screen"
        );

        System.out.println(
            "Mobile Only"
        );
    }
}


class FamilyPlan extends SubscriptionPlan {

    public FamilyPlan() {
        super("Family", 499);
    }

    @Override
    public void displayFeatures() {

        System.out.println(
            "Family Plan"
        );

        System.out.println(
            "HD Quality"
        );

        System.out.println(
            "2 Screens"
        );

        System.out.println(
            "Shared Household"
        );
    }
}


