// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: MessageProtobuf.proto

package com.example.example.bean;

public final class MessageProtobuf {
  private MessageProtobuf() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MsgOrBuilder extends
      // @@protoc_insertion_point(interface_extends:Msg)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string msgId = 1;</code>
     * @return The msgId.
     */
    String getMsgId();
    /**
     * <code>string msgId = 1;</code>
     * @return The bytes for msgId.
     */
    com.google.protobuf.ByteString
        getMsgIdBytes();

    /**
     * <code>int32 msgType = 2;</code>
     * @return The msgType.
     */
    int getMsgType();

    /**
     * <code>int32 msgContentType = 3;</code>
     * @return The msgContentType.
     */
    int getMsgContentType();

    /**
     * <code>string fromId = 4;</code>
     * @return The fromId.
     */
    String getFromId();
    /**
     * <code>string fromId = 4;</code>
     * @return The bytes for fromId.
     */
    com.google.protobuf.ByteString
        getFromIdBytes();

    /**
     * <code>string toId = 5;</code>
     * @return The toId.
     */
    String getToId();
    /**
     * <code>string toId = 5;</code>
     * @return The bytes for toId.
     */
    com.google.protobuf.ByteString
        getToIdBytes();

    /**
     * <code>int64 timestamp = 6;</code>
     * @return The timestamp.
     */
    long getTimestamp();

    /**
     * <code>int32 status = 7;</code>
     * @return The status.
     */
    int getStatus();

    /**
     * <code>string extend = 8;</code>
     * @return The extend.
     */
    String getExtend();
    /**
     * <code>string extend = 8;</code>
     * @return The bytes for extend.
     */
    com.google.protobuf.ByteString
        getExtendBytes();
  }
  /**
   * Protobuf type {@code Msg}
   */
  public static final class Msg extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:Msg)
      MsgOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Msg.newBuilder() to construct.
    private Msg(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Msg() {
      msgId_ = "";
      fromId_ = "";
      toId_ = "";
      extend_ = "";
    }

    @Override
    @SuppressWarnings({"unused"})
    protected Object newInstance(
        UnusedPrivateParameter unused) {
      return new Msg();
    }

    @Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Msg(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new NullPointerException();
      }
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              String s = input.readStringRequireUtf8();

              msgId_ = s;
              break;
            }
            case 16: {

              msgType_ = input.readInt32();
              break;
            }
            case 24: {

              msgContentType_ = input.readInt32();
              break;
            }
            case 34: {
              String s = input.readStringRequireUtf8();

              fromId_ = s;
              break;
            }
            case 42: {
              String s = input.readStringRequireUtf8();

              toId_ = s;
              break;
            }
            case 48: {

              timestamp_ = input.readInt64();
              break;
            }
            case 56: {

              status_ = input.readInt32();
              break;
            }
            case 66: {
              String s = input.readStringRequireUtf8();

              extend_ = s;
              break;
            }
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return MessageProtobuf.internal_static_Msg_descriptor;
    }

    @Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return MessageProtobuf.internal_static_Msg_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              Msg.class, Builder.class);
    }

    public static final int MSGID_FIELD_NUMBER = 1;
    private volatile Object msgId_;
    /**
     * <code>string msgId = 1;</code>
     * @return The msgId.
     */
    @Override
    public String getMsgId() {
      Object ref = msgId_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        msgId_ = s;
        return s;
      }
    }
    /**
     * <code>string msgId = 1;</code>
     * @return The bytes for msgId.
     */
    @Override
    public com.google.protobuf.ByteString
        getMsgIdBytes() {
      Object ref = msgId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        msgId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int MSGTYPE_FIELD_NUMBER = 2;
    private int msgType_;
    /**
     * <code>int32 msgType = 2;</code>
     * @return The msgType.
     */
    @Override
    public int getMsgType() {
      return msgType_;
    }

    public static final int MSGCONTENTTYPE_FIELD_NUMBER = 3;
    private int msgContentType_;
    /**
     * <code>int32 msgContentType = 3;</code>
     * @return The msgContentType.
     */
    @Override
    public int getMsgContentType() {
      return msgContentType_;
    }

    public static final int FROMID_FIELD_NUMBER = 4;
    private volatile Object fromId_;
    /**
     * <code>string fromId = 4;</code>
     * @return The fromId.
     */
    @Override
    public String getFromId() {
      Object ref = fromId_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        fromId_ = s;
        return s;
      }
    }
    /**
     * <code>string fromId = 4;</code>
     * @return The bytes for fromId.
     */
    @Override
    public com.google.protobuf.ByteString
        getFromIdBytes() {
      Object ref = fromId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        fromId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TOID_FIELD_NUMBER = 5;
    private volatile Object toId_;
    /**
     * <code>string toId = 5;</code>
     * @return The toId.
     */
    @Override
    public String getToId() {
      Object ref = toId_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        toId_ = s;
        return s;
      }
    }
    /**
     * <code>string toId = 5;</code>
     * @return The bytes for toId.
     */
    @Override
    public com.google.protobuf.ByteString
        getToIdBytes() {
      Object ref = toId_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        toId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int TIMESTAMP_FIELD_NUMBER = 6;
    private long timestamp_;
    /**
     * <code>int64 timestamp = 6;</code>
     * @return The timestamp.
     */
    @Override
    public long getTimestamp() {
      return timestamp_;
    }

    public static final int STATUS_FIELD_NUMBER = 7;
    private int status_;
    /**
     * <code>int32 status = 7;</code>
     * @return The status.
     */
    @Override
    public int getStatus() {
      return status_;
    }

    public static final int EXTEND_FIELD_NUMBER = 8;
    private volatile Object extend_;
    /**
     * <code>string extend = 8;</code>
     * @return The extend.
     */
    @Override
    public String getExtend() {
      Object ref = extend_;
      if (ref instanceof String) {
        return (String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        String s = bs.toStringUtf8();
        extend_ = s;
        return s;
      }
    }
    /**
     * <code>string extend = 8;</code>
     * @return The bytes for extend.
     */
    @Override
    public com.google.protobuf.ByteString
        getExtendBytes() {
      Object ref = extend_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (String) ref);
        extend_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    @Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(msgId_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, msgId_);
      }
      if (msgType_ != 0) {
        output.writeInt32(2, msgType_);
      }
      if (msgContentType_ != 0) {
        output.writeInt32(3, msgContentType_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(fromId_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, fromId_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(toId_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 5, toId_);
      }
      if (timestamp_ != 0L) {
        output.writeInt64(6, timestamp_);
      }
      if (status_ != 0) {
        output.writeInt32(7, status_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(extend_)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 8, extend_);
      }
      unknownFields.writeTo(output);
    }

    @Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(msgId_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, msgId_);
      }
      if (msgType_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, msgType_);
      }
      if (msgContentType_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, msgContentType_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(fromId_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(4, fromId_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(toId_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(5, toId_);
      }
      if (timestamp_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(6, timestamp_);
      }
      if (status_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(7, status_);
      }
      if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(extend_)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(8, extend_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @Override
    public boolean equals(final Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof Msg)) {
        return super.equals(obj);
      }
      Msg other = (Msg) obj;

      if (!getMsgId()
          .equals(other.getMsgId())) return false;
      if (getMsgType()
          != other.getMsgType()) return false;
      if (getMsgContentType()
          != other.getMsgContentType()) return false;
      if (!getFromId()
          .equals(other.getFromId())) return false;
      if (!getToId()
          .equals(other.getToId())) return false;
      if (getTimestamp()
          != other.getTimestamp()) return false;
      if (getStatus()
          != other.getStatus()) return false;
      if (!getExtend()
          .equals(other.getExtend())) return false;
      if (!unknownFields.equals(other.unknownFields)) return false;
      return true;
    }

    @Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + MSGID_FIELD_NUMBER;
      hash = (53 * hash) + getMsgId().hashCode();
      hash = (37 * hash) + MSGTYPE_FIELD_NUMBER;
      hash = (53 * hash) + getMsgType();
      hash = (37 * hash) + MSGCONTENTTYPE_FIELD_NUMBER;
      hash = (53 * hash) + getMsgContentType();
      hash = (37 * hash) + FROMID_FIELD_NUMBER;
      hash = (53 * hash) + getFromId().hashCode();
      hash = (37 * hash) + TOID_FIELD_NUMBER;
      hash = (53 * hash) + getToId().hashCode();
      hash = (37 * hash) + TIMESTAMP_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getTimestamp());
      hash = (37 * hash) + STATUS_FIELD_NUMBER;
      hash = (53 * hash) + getStatus();
      hash = (37 * hash) + EXTEND_FIELD_NUMBER;
      hash = (53 * hash) + getExtend().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static Msg parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Msg parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Msg parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Msg parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Msg parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static Msg parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static Msg parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Msg parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static Msg parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static Msg parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static Msg parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static Msg parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(Msg prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code Msg}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:Msg)
        MsgOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return MessageProtobuf.internal_static_Msg_descriptor;
      }

      @Override
      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return MessageProtobuf.internal_static_Msg_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                Msg.class, Builder.class);
      }

      // Construct using com.example.example.model.MessageProtobuf.Msg.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      @Override
      public Builder clear() {
        super.clear();
        msgId_ = "";

        msgType_ = 0;

        msgContentType_ = 0;

        fromId_ = "";

        toId_ = "";

        timestamp_ = 0L;

        status_ = 0;

        extend_ = "";

        return this;
      }

      @Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return MessageProtobuf.internal_static_Msg_descriptor;
      }

      @Override
      public Msg getDefaultInstanceForType() {
        return Msg.getDefaultInstance();
      }

      @Override
      public Msg build() {
        Msg result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @Override
      public Msg buildPartial() {
        Msg result = new Msg(this);
        result.msgId_ = msgId_;
        result.msgType_ = msgType_;
        result.msgContentType_ = msgContentType_;
        result.fromId_ = fromId_;
        result.toId_ = toId_;
        result.timestamp_ = timestamp_;
        result.status_ = status_;
        result.extend_ = extend_;
        onBuilt();
        return result;
      }

      @Override
      public Builder clone() {
        return super.clone();
      }
      @Override
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.setField(field, value);
      }
      @Override
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return super.clearField(field);
      }
      @Override
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return super.clearOneof(oneof);
      }
      @Override
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return super.setRepeatedField(field, index, value);
      }
      @Override
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return super.addRepeatedField(field, value);
      }
      @Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof Msg) {
          return mergeFrom((Msg)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(Msg other) {
        if (other == Msg.getDefaultInstance()) return this;
        if (!other.getMsgId().isEmpty()) {
          msgId_ = other.msgId_;
          onChanged();
        }
        if (other.getMsgType() != 0) {
          setMsgType(other.getMsgType());
        }
        if (other.getMsgContentType() != 0) {
          setMsgContentType(other.getMsgContentType());
        }
        if (!other.getFromId().isEmpty()) {
          fromId_ = other.fromId_;
          onChanged();
        }
        if (!other.getToId().isEmpty()) {
          toId_ = other.toId_;
          onChanged();
        }
        if (other.getTimestamp() != 0L) {
          setTimestamp(other.getTimestamp());
        }
        if (other.getStatus() != 0) {
          setStatus(other.getStatus());
        }
        if (!other.getExtend().isEmpty()) {
          extend_ = other.extend_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      @Override
      public final boolean isInitialized() {
        return true;
      }

      @Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        Msg parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (Msg) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private Object msgId_ = "";
      /**
       * <code>string msgId = 1;</code>
       * @return The msgId.
       */
      public String getMsgId() {
        Object ref = msgId_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          msgId_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string msgId = 1;</code>
       * @return The bytes for msgId.
       */
      public com.google.protobuf.ByteString
          getMsgIdBytes() {
        Object ref = msgId_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          msgId_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string msgId = 1;</code>
       * @param value The msgId to set.
       * @return This builder for chaining.
       */
      public Builder setMsgId(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        msgId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string msgId = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearMsgId() {
        
        msgId_ = getDefaultInstance().getMsgId();
        onChanged();
        return this;
      }
      /**
       * <code>string msgId = 1;</code>
       * @param value The bytes for msgId to set.
       * @return This builder for chaining.
       */
      public Builder setMsgIdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        msgId_ = value;
        onChanged();
        return this;
      }

      private int msgType_ ;
      /**
       * <code>int32 msgType = 2;</code>
       * @return The msgType.
       */
      @Override
      public int getMsgType() {
        return msgType_;
      }
      /**
       * <code>int32 msgType = 2;</code>
       * @param value The msgType to set.
       * @return This builder for chaining.
       */
      public Builder setMsgType(int value) {
        
        msgType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 msgType = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearMsgType() {
        
        msgType_ = 0;
        onChanged();
        return this;
      }

      private int msgContentType_ ;
      /**
       * <code>int32 msgContentType = 3;</code>
       * @return The msgContentType.
       */
      @Override
      public int getMsgContentType() {
        return msgContentType_;
      }
      /**
       * <code>int32 msgContentType = 3;</code>
       * @param value The msgContentType to set.
       * @return This builder for chaining.
       */
      public Builder setMsgContentType(int value) {
        
        msgContentType_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 msgContentType = 3;</code>
       * @return This builder for chaining.
       */
      public Builder clearMsgContentType() {
        
        msgContentType_ = 0;
        onChanged();
        return this;
      }

      private Object fromId_ = "";
      /**
       * <code>string fromId = 4;</code>
       * @return The fromId.
       */
      public String getFromId() {
        Object ref = fromId_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          fromId_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string fromId = 4;</code>
       * @return The bytes for fromId.
       */
      public com.google.protobuf.ByteString
          getFromIdBytes() {
        Object ref = fromId_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          fromId_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string fromId = 4;</code>
       * @param value The fromId to set.
       * @return This builder for chaining.
       */
      public Builder setFromId(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        fromId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string fromId = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearFromId() {
        
        fromId_ = getDefaultInstance().getFromId();
        onChanged();
        return this;
      }
      /**
       * <code>string fromId = 4;</code>
       * @param value The bytes for fromId to set.
       * @return This builder for chaining.
       */
      public Builder setFromIdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        fromId_ = value;
        onChanged();
        return this;
      }

      private Object toId_ = "";
      /**
       * <code>string toId = 5;</code>
       * @return The toId.
       */
      public String getToId() {
        Object ref = toId_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          toId_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string toId = 5;</code>
       * @return The bytes for toId.
       */
      public com.google.protobuf.ByteString
          getToIdBytes() {
        Object ref = toId_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          toId_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string toId = 5;</code>
       * @param value The toId to set.
       * @return This builder for chaining.
       */
      public Builder setToId(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        toId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string toId = 5;</code>
       * @return This builder for chaining.
       */
      public Builder clearToId() {
        
        toId_ = getDefaultInstance().getToId();
        onChanged();
        return this;
      }
      /**
       * <code>string toId = 5;</code>
       * @param value The bytes for toId to set.
       * @return This builder for chaining.
       */
      public Builder setToIdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        toId_ = value;
        onChanged();
        return this;
      }

      private long timestamp_ ;
      /**
       * <code>int64 timestamp = 6;</code>
       * @return The timestamp.
       */
      @Override
      public long getTimestamp() {
        return timestamp_;
      }
      /**
       * <code>int64 timestamp = 6;</code>
       * @param value The timestamp to set.
       * @return This builder for chaining.
       */
      public Builder setTimestamp(long value) {
        
        timestamp_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 timestamp = 6;</code>
       * @return This builder for chaining.
       */
      public Builder clearTimestamp() {
        
        timestamp_ = 0L;
        onChanged();
        return this;
      }

      private int status_ ;
      /**
       * <code>int32 status = 7;</code>
       * @return The status.
       */
      @Override
      public int getStatus() {
        return status_;
      }
      /**
       * <code>int32 status = 7;</code>
       * @param value The status to set.
       * @return This builder for chaining.
       */
      public Builder setStatus(int value) {
        
        status_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int32 status = 7;</code>
       * @return This builder for chaining.
       */
      public Builder clearStatus() {
        
        status_ = 0;
        onChanged();
        return this;
      }

      private Object extend_ = "";
      /**
       * <code>string extend = 8;</code>
       * @return The extend.
       */
      public String getExtend() {
        Object ref = extend_;
        if (!(ref instanceof String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          String s = bs.toStringUtf8();
          extend_ = s;
          return s;
        } else {
          return (String) ref;
        }
      }
      /**
       * <code>string extend = 8;</code>
       * @return The bytes for extend.
       */
      public com.google.protobuf.ByteString
          getExtendBytes() {
        Object ref = extend_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (String) ref);
          extend_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string extend = 8;</code>
       * @param value The extend to set.
       * @return This builder for chaining.
       */
      public Builder setExtend(
          String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        extend_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string extend = 8;</code>
       * @return This builder for chaining.
       */
      public Builder clearExtend() {
        
        extend_ = getDefaultInstance().getExtend();
        onChanged();
        return this;
      }
      /**
       * <code>string extend = 8;</code>
       * @param value The bytes for extend to set.
       * @return This builder for chaining.
       */
      public Builder setExtendBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        extend_ = value;
        onChanged();
        return this;
      }
      @Override
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      @Override
      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:Msg)
    }

    // @@protoc_insertion_point(class_scope:Msg)
    private static final Msg DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new Msg();
    }

    public static Msg getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Msg>
        PARSER = new com.google.protobuf.AbstractParser<Msg>() {
      @Override
      public Msg parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new Msg(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Msg> parser() {
      return PARSER;
    }

    @Override
    public com.google.protobuf.Parser<Msg> getParserForType() {
      return PARSER;
    }

    @Override
    public Msg getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_Msg_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_Msg_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    String[] descriptorData = {
      "\n\025MessageProtobuf.proto\"\216\001\n\003Msg\022\r\n\005msgId" +
      "\030\001 \001(\t\022\017\n\007msgType\030\002 \001(\005\022\026\n\016msgContentTyp" +
      "e\030\003 \001(\005\022\016\n\006fromId\030\004 \001(\t\022\014\n\004toId\030\005 \001(\t\022\021\n" +
      "\ttimestamp\030\006 \001(\003\022\016\n\006status\030\007 \001(\005\022\016\n\006exte" +
      "nd\030\010 \001(\tB,\n\031com.example.example.modelB\017M" +
      "essageProtobufb\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_Msg_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_Msg_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_Msg_descriptor,
        new String[] { "MsgId", "MsgType", "MsgContentType", "FromId", "ToId", "Timestamp", "Status", "Extend", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
