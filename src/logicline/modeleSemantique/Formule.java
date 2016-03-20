package logicline.modeleSemantique;

import java.util.*;

public abstract class Formule {
	
	protected Set<String> libres;
	
	public Formule(){
		this.libres = new HashSet<String>();
	}

	//retourne une représentation ASCII de la formule logique
	public abstract String toString();

	//supprime toutes les implications de la formule
	protected Formule supprImplications() { 
		return this;
	}

	//déplace les non à l'intérieur des formules
	protected Formule entrerNegations() { 
		return this.negation(); 
	}

	//Retourne la formule représentant la négation de this
	protected Formule negation() { 
		return new Non(this); 
		}

	//Retourne vrai si la formule contient un Et
	protected boolean contientEt() {
		return false; 
	}

	//Retourne une formule équivalente à OU(this, d)
	protected Formule ougauche(Formule d) { 
		return d.oudroite(this); 
	}

	//Retourne une formule équivalente à OU(g, this), g ne contenant pas de ET
	protected Formule oudroite(Formule g) { 
		
		if (this instanceof Et) {
			
			Formule fg = ((Et) this).formuleGauche;
			Formule fd = ((Et) this).formuleDroite;
			
			return new Et(new Ou(g,fg), new Ou(g,fd));
		}
		
		else {
			return new Ou(g,this);
		}
	}

	//déplace les et à l'intérieur des formules
	protected Formule entrerDisjonctions() {return this; }

	//transforme la formule en FNC
	public Formule fnc() 
	{ 
		Formule f = this.supprImplications();
		f = f.entrerNegations();
		f = f.entrerDisjonctions();
		return f; 
	}

	//retourne la liste des clauses d'une formule en FNC
	public ListeClauses clauses() throws NotFNCException, TrueClauseException, FalseClauseException, VariableClauseException 
	{
		if (this instanceof Et) {
			Formule fg = ((Et) this).formuleGauche;
			Formule fd = ((Et) this).formuleDroite;
			ListeClauses clauses = new ListeClauses();
			clauses.addAll(fg.clauses());
			clauses.addAll(fd.clauses());
			return clauses;
		} 
		
		else 
		{	
		throw new NotFNCException();
		}
	}
	
	//méthode qui retourne la clause obtenue 
	public Clause clause() throws TrueClauseException, FalseClauseException, NotFNCException {
		throw new NotFNCException();
	}

	//retourne la liste des noms des variables libres de la formule
	public abstract Set<String> variablesLibres();

	//effectue une substitution dans une formule
	public abstract Formule substitue(Substitution s);

	//retourne l'évaluation de la formule
	public abstract boolean valeur() throws VariableLibreException;

	//affiche la table de vérité de la formule
	public void tableVerite() 
	{
		
		this.libres = this.variablesLibres();
		Iterator<String> it = this.libres.iterator();
		List<Substitution> valFinale = new ArrayList<Substitution>();
		
		while (it.hasNext()) {
			
			String variable = it.next();
			List<Substitution> valeurs = valFinale;
			
			System.out.print(variable + " | ");
			
			if (valeurs.isEmpty()) {
				
				Substitution s1 = new Substitution();
				Substitution s2 = new Substitution();
				
				s1.set(variable, new Constante(false));
				s2.set(variable, new Constante(true));
				
				valFinale.add(s1);
				valFinale.add(s2);
				
				valeurs = valFinale;
				
			}
			
			else {
				
				valeurs = valFinale;
				Iterator<Substitution> tmp = valeurs.iterator();
				Substitution s = tmp.next();
				
				while (tmp.hasNext()) {
					
					valFinale.remove(s);
					System.out.println(valeurs);
					
					Substitution s1 = new Substitution(s);
					Substitution s2 = new Substitution(s);
					
					s1.set(variable, new Constante(false));
					s2.set(variable,new Constante(true));
					
					valFinale.add(s1);
					valFinale.add(s2);
					
					s = tmp.next();
					System.out.println(valFinale);
					
					
				}
			}
			
		}
		System.out.println(valFinale);
		
		System.out.println(this.toString());
		
		Iterator<Substitution> it2 = valFinale.iterator();
		
		while (it2.hasNext()) {
			
			Substitution s = it2.next();
			
			Iterator<String> itvar = this.libres.iterator();
			
			while (itvar.hasNext()) {

				String variable = itvar.next();
				
				System.out.print(s.get(variable).toString() + " | ");
				
			}
			
			try {
				Boolean resultat = this.substitue(s).valeur();
				System.out.println(new Constante(resultat).toString());
				
			} 
			catch (VariableLibreException e) {
				e.printStackTrace();
			}
			
			
			
			
		}
		
		
		
	}
}
