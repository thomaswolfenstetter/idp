package de.tum.pssif.core.metamodel.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.google.common.collect.Sets;

import de.tum.pssif.core.exception.PSSIFStructuralIntegrityException;
import de.tum.pssif.core.metamodel.Attribute;
import de.tum.pssif.core.metamodel.AttributeGroup;
import de.tum.pssif.core.metamodel.DataType;
import de.tum.pssif.core.metamodel.ElementType;
import de.tum.pssif.core.metamodel.PrimitiveDataType;
import de.tum.pssif.core.metamodel.Unit;
import de.tum.pssif.core.metamodel.Units;
import de.tum.pssif.core.util.PSSIFUtil;


public abstract class ElementTypeImpl<T extends ElementType<T>> extends NamedImpl implements ElementType<T> {
  private T                             general         = null;
  private final Set<T>                  specializations = Sets.newHashSet();
  private final Set<AttributeGroupImpl> attributeGroups = Sets.newHashSet();

  public ElementTypeImpl(String name) {
    super(name);
    this.attributeGroups.add(new AttributeGroupImpl(AttributeGroup.DEFAULT_GROUP_NAME));
  }

  @Override
  public T getGeneral() {
    return general;
  }

  @Override
  public Collection<T> getSpecials() {
    return Collections.unmodifiableCollection(specializations);
  }

  //TODO get rid of the cast
  @SuppressWarnings("unchecked")
  @Override
  public void inherit(T general) {
    this.general = general;
    general.registerSpecialization((T) this);
  }

  @Override
  public void registerSpecialization(T special) {
    specializations.add(special);
  }

  @Override
  public Attribute findAttribute(String name) {
    Attribute result = null;
    for (AttributeGroup group : getAttributeGroups()) {
      result = group.findAttribute(name);
      if (result != null) {
        return result;
      }
    }
    return null;
  }

  @Override
  public Collection<Attribute> getAttributes() {
    Collection<Attribute> attrs = Sets.newHashSet();
    for (AttributeGroup group : getAttributeGroups()) {
      attrs.addAll(group.getAttributes());
    }
    return Collections.unmodifiableCollection(attrs);
  }

  @Override
  public Attribute createAttribute(AttributeGroup group, String name, DataType type, Unit unit, boolean visible) {
    if (name == null || name.trim().isEmpty()) {
      throw new PSSIFStructuralIntegrityException("name can not be null or empty");
    }
    if (findAttribute(name) != null) {
      throw new PSSIFStructuralIntegrityException("duplicate attribute with name " + name);
    }
    if (!(PrimitiveDataType.DECIMAL.equals(type) || PrimitiveDataType.INTEGER.equals(type)) && !Units.NONE.equals(unit)) {
      throw new PSSIFStructuralIntegrityException("Only numeric attributes can have units!");
    }
    AttributeGroupImpl actual = findAttributeGroup(group.getName());
    if (actual == null) {
      throw new PSSIFStructuralIntegrityException("Provided attribute group not part of this type.");
    }
    AttributeImpl result = new AttributeImpl(name, type, unit, visible);
    actual.addAttribute(result);
    return result;
  }

  @Override
  public Attribute createAttribute(AttributeGroup group, String name, DataType dataType, boolean visible) {
    return createAttribute(group, name, dataType, Units.NONE, visible);
  }

  @Override
  public void removeAttribute(Attribute attribute) {
    for (AttributeGroup group : getAttributeGroups()) {
      if (group.findAttribute(attribute.getName()) != null) {
        group.removeAttribute(attribute);
      }
    }
  }

  @Override
  public AttributeGroup createAttributeGroup(String name) {
    if (PSSIFUtil.normalize(name).isEmpty()) {
      throw new PSSIFStructuralIntegrityException("The name of an attrobute group can not be null or empty!");
    }
    if (findAttributeGroup(name) != null) {
      throw new PSSIFStructuralIntegrityException("An attribute group with the name " + name + " already exists for element type " + getName());
    }
    AttributeGroupImpl result = new AttributeGroupImpl(name);
    this.attributeGroups.add(result);
    return result;
  }

  @Override
  public Collection<AttributeGroup> getAttributeGroups() {
    return Collections.<AttributeGroup> unmodifiableCollection(this.attributeGroups);
  }

  @Override
  public AttributeGroup getDefaultAttributeGroup() {
    return findAttributeGroup(AttributeGroup.DEFAULT_GROUP_NAME);
  }

  @Override
  public AttributeGroupImpl findAttributeGroup(String name) {
    for (AttributeGroupImpl atg : this.attributeGroups) {
      if (PSSIFUtil.areSame(name, atg.getName())) {
        return atg;
      }
    }
    return null;
  }

  @Override
  public void removeAttributeGroup(AttributeGroup group) {
    if (PSSIFUtil.areSame(group.getName(), AttributeGroup.DEFAULT_GROUP_NAME)) {
      throw new PSSIFStructuralIntegrityException("The default attribute group can not be removed!");
    }
    AttributeGroupImpl actual = findAttributeGroup(group.getName());
    this.attributeGroups.remove(actual);
  }
}
