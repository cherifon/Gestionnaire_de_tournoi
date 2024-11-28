package model;

public abstract class Personne {
    private String nom;
    private String prenom;
    private int age;

    protected Personne(String nom, String prenom, int age) {
        if (nom == null || nom.trim().isEmpty()) { // Vérifier que le nom n'est pas vide (.trim() enlève les espaces en
                                                   // début et fin de chaîne)
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        if (prenom == null || prenom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom ne peut pas être vide.");
        }
        if (age < 0) {
            throw new IllegalArgumentException("L'âge doit être positif.");
        }
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("L'âge doit être positif.");
        }
        this.age = age;
    }

    @Override
    public String toString() {
        return "age=" + age + ", nom=" + nom + ", prenom=" + prenom;
    }
}
