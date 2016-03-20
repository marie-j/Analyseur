package logicline.modeleSemantique;

import java.util.Set;

public class Equivalence extends Formule {
	
	Formule formuleGauche;
	Formule formuleDroite;
	
	public Equivalence(Formule gauche,Formule droite) {
		this.formuleGauche = gauche;
		this.formuleDroite = droite;
	}

	@Override
	public String toString() {
		return "(" + formuleGauche.toString() + ") ⇔ (" + this.formuleDroite.toString() + ")";
	}

	@Override
	public Set<String> variablesLibres() {
		Set<String> libresGauche = formuleGauche.variablesLibres();
		Set<String> libresDroite = formuleDroite.variablesLibres();
		this.libres.addAll(libresGauche);
		this.libres.addAll(libresDroite);
		return this.libres;
	}

	@Override
	public Formule substitue(Substitution s) {
		return new Equivalence(this.formuleGauche.substitue(s),this.formuleDroite.substitue(s));
	}

	@Override
	public boolean valeur() throws VariableLibreException {
		if (this.variablesLibres().isEmpty()) {
			return this.formuleGauche.valeur() == this.formuleDroite.valeur();
		}
		else {
			throw new VariableLibreException("");
		}
	}
	
	@Override
	public Formule supprImplications() {
		Formule fg = this.formuleGauche.supprImplications();
		Formule fd = this.formuleDroite.supprImplications();
		return new Et(new Ou(new Non(fg),fd),new Ou(fg,new Non(fd)));
	}

}
