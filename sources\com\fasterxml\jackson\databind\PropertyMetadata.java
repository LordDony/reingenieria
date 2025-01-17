package com.fasterxml.jackson.databind;

import com.fasterxml.jackson.annotation.Nulls;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import java.io.Serializable;

public class PropertyMetadata implements Serializable {
    public static final PropertyMetadata STD_OPTIONAL;
    public static final PropertyMetadata STD_REQUIRED;
    public static final PropertyMetadata STD_REQUIRED_OR_OPTIONAL;
    protected Nulls _contentNulls;
    protected final String _defaultValue;
    protected final String _description;
    protected final Integer _index;
    protected final transient MergeInfo _mergeInfo;
    protected final Boolean _required;
    protected Nulls _valueNulls;

    public static final class MergeInfo {
        public final boolean fromDefaults;
        public final AnnotatedMember getter;

        protected MergeInfo(AnnotatedMember annotatedMember, boolean z) {
            this.getter = annotatedMember;
            this.fromDefaults = z;
        }

        public static MergeInfo createForDefaults(AnnotatedMember annotatedMember) {
            return new MergeInfo(annotatedMember, true);
        }

        public static MergeInfo createForPropertyOverride(AnnotatedMember annotatedMember) {
            return new MergeInfo(annotatedMember, false);
        }

        public static MergeInfo createForTypeOverride(AnnotatedMember annotatedMember) {
            return new MergeInfo(annotatedMember, false);
        }
    }

    static {
        PropertyMetadata propertyMetadata = new PropertyMetadata(Boolean.TRUE, null, null, null, null, null, null);
        STD_REQUIRED = propertyMetadata;
        PropertyMetadata propertyMetadata2 = new PropertyMetadata(Boolean.FALSE, null, null, null, null, null, null);
        STD_OPTIONAL = propertyMetadata2;
        PropertyMetadata propertyMetadata3 = new PropertyMetadata(null, null, null, null, null, null, null);
        STD_REQUIRED_OR_OPTIONAL = propertyMetadata3;
    }

    protected PropertyMetadata(Boolean bool, String str, Integer num, String str2, MergeInfo mergeInfo, Nulls nulls, Nulls nulls2) {
        this._required = bool;
        this._description = str;
        this._index = num;
        if (str2 == null || str2.isEmpty()) {
            str2 = null;
        }
        this._defaultValue = str2;
        this._mergeInfo = mergeInfo;
        this._valueNulls = nulls;
        this._contentNulls = nulls2;
    }

    public static PropertyMetadata construct(Boolean bool, String str, Integer num, String str2) {
        if (str != null || num != null || str2 != null) {
            PropertyMetadata propertyMetadata = new PropertyMetadata(bool, str, num, str2, null, null, null);
            return propertyMetadata;
        } else if (bool == null) {
            return STD_REQUIRED_OR_OPTIONAL;
        } else {
            return bool.booleanValue() ? STD_REQUIRED : STD_OPTIONAL;
        }
    }

    public Nulls getContentNulls() {
        return this._contentNulls;
    }

    public MergeInfo getMergeInfo() {
        return this._mergeInfo;
    }

    public Nulls getValueNulls() {
        return this._valueNulls;
    }

    public boolean isRequired() {
        Boolean bool = this._required;
        return bool != null && bool.booleanValue();
    }

    /* access modifiers changed from: protected */
    public Object readResolve() {
        if (this._description != null || this._index != null || this._defaultValue != null || this._mergeInfo != null || this._valueNulls != null || this._contentNulls != null) {
            return this;
        }
        Boolean bool = this._required;
        if (bool == null) {
            return STD_REQUIRED_OR_OPTIONAL;
        }
        return bool.booleanValue() ? STD_REQUIRED : STD_OPTIONAL;
    }

    public PropertyMetadata withDescription(String str) {
        PropertyMetadata propertyMetadata = new PropertyMetadata(this._required, str, this._index, this._defaultValue, this._mergeInfo, this._valueNulls, this._contentNulls);
        return propertyMetadata;
    }

    public PropertyMetadata withMergeInfo(MergeInfo mergeInfo) {
        PropertyMetadata propertyMetadata = new PropertyMetadata(this._required, this._description, this._index, this._defaultValue, mergeInfo, this._valueNulls, this._contentNulls);
        return propertyMetadata;
    }

    public PropertyMetadata withNulls(Nulls nulls, Nulls nulls2) {
        PropertyMetadata propertyMetadata = new PropertyMetadata(this._required, this._description, this._index, this._defaultValue, this._mergeInfo, nulls, nulls2);
        return propertyMetadata;
    }
}
