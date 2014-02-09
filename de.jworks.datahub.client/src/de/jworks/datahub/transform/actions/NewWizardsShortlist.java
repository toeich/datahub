package de.jworks.datahub.transform.actions;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.actions.ContributionItemFactory;

public class NewWizardsShortlist extends CompoundContributionItem {

	@Override
	protected IContributionItem[] getContributionItems() {
		return new IContributionItem[] {
				ContributionItemFactory.NEW_WIZARD_SHORTLIST.create(PlatformUI.getWorkbench().getActiveWorkbenchWindow())
		};
	}

}
