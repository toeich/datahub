package de.jworks.datahub.transform.actions;

import org.eclipse.jface.action.IContributionItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.CompoundContributionItem;
import org.eclipse.ui.actions.ContributionItemFactory;

public class PerspectivesShortlist extends CompoundContributionItem {

	@Override
	protected IContributionItem[] getContributionItems() {
		return new IContributionItem[] {
				ContributionItemFactory.PERSPECTIVES_SHORTLIST.create(PlatformUI.getWorkbench().getActiveWorkbenchWindow())
		};
	}

}
