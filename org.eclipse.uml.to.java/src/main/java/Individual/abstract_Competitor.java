package Individual;
import Interface.Competitor;

 public abstract class abstract_Competitor implements competitor{
	 private String abstractCompetitorName;
	 	 
	 public abstract void addMember(abstract_Competitor member);
	 public abstract void deleteMember(abstract_Competitor member);
	 public abstract boolean hasValidName();
	 
	 public void setAbstractCompetitorName(String newAbstractCompetitorName) {
			this.abstractCompetitorName = newAbstractCompetitorName;
		}
	 
	 public String getAbstractCompetitorName() {
			return this.abstractCompetitorName;
		}
}
