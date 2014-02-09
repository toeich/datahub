package de.jworks.datahub.business.transform.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.NONE)
@XmlSeeAlso({ Constant.class, Function.class, Operation.class, Filter.class, Lookup.class })
public abstract class TransformationComponent extends Component {

	private static final long serialVersionUID = 1L;
}
