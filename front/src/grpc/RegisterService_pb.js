// source: RegisterService.proto
/**
 * @fileoverview
 * @enhanceable
 * @suppress {missingRequire} reports error on implicit type usages.
 * @suppress {messageConventions} JS Compiler reports an error if a variable or
 *     field starts with 'MSG_' and isn't a translatable message.
 * @public
 */
// GENERATED CODE -- DO NOT EDIT!
/* eslint-disable */
// @ts-nocheck

var jspb = require('google-protobuf');
var goog = jspb;
var global = Function('return this')();

var common_pb = require('./common_pb.js');
goog.object.extend(proto, common_pb);
goog.exportSymbol('proto.ru.miet.example.grpc.chat.service.RegisterRequest', null, global);
goog.exportSymbol('proto.ru.miet.example.grpc.chat.service.RegisterResponse', null, global);
/**
 * Generated by JsPbCodeGenerator.
 * @param {Array=} opt_data Optional initial data array, typically from a
 * server response, or constructed directly in Javascript. The array is used
 * in place and becomes part of the constructed object. It is not cloned.
 * If no data is provided, the constructed object will be empty, but still
 * valid.
 * @extends {jspb.Message}
 * @constructor
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest = function(opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null);
};
goog.inherits(proto.ru.miet.example.grpc.chat.service.RegisterRequest, jspb.Message);
if (goog.DEBUG && !COMPILED) {
  /**
   * @public
   * @override
   */
  proto.ru.miet.example.grpc.chat.service.RegisterRequest.displayName = 'proto.ru.miet.example.grpc.chat.service.RegisterRequest';
}
/**
 * Generated by JsPbCodeGenerator.
 * @param {Array=} opt_data Optional initial data array, typically from a
 * server response, or constructed directly in Javascript. The array is used
 * in place and becomes part of the constructed object. It is not cloned.
 * If no data is provided, the constructed object will be empty, but still
 * valid.
 * @extends {jspb.Message}
 * @constructor
 */
proto.ru.miet.example.grpc.chat.service.RegisterResponse = function(opt_data) {
  jspb.Message.initialize(this, opt_data, 0, -1, null, null);
};
goog.inherits(proto.ru.miet.example.grpc.chat.service.RegisterResponse, jspb.Message);
if (goog.DEBUG && !COMPILED) {
  /**
   * @public
   * @override
   */
  proto.ru.miet.example.grpc.chat.service.RegisterResponse.displayName = 'proto.ru.miet.example.grpc.chat.service.RegisterResponse';
}



if (jspb.Message.GENERATE_TO_OBJECT) {
/**
 * Creates an object representation of this proto.
 * Field names that are reserved in JavaScript and will be renamed to pb_name.
 * Optional fields that are not set will be set to undefined.
 * To access a reserved field use, foo.pb_<name>, eg, foo.pb_default.
 * For the list of reserved names please see:
 *     net/proto2/compiler/js/internal/generator.cc#kKeyword.
 * @param {boolean=} opt_includeInstance Deprecated. whether to include the
 *     JSPB instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @return {!Object}
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest.prototype.toObject = function(opt_includeInstance) {
  return proto.ru.miet.example.grpc.chat.service.RegisterRequest.toObject(opt_includeInstance, this);
};


/**
 * Static version of the {@see toObject} method.
 * @param {boolean|undefined} includeInstance Deprecated. Whether to include
 *     the JSPB instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @param {!proto.ru.miet.example.grpc.chat.service.RegisterRequest} msg The msg instance to transform.
 * @return {!Object}
 * @suppress {unusedLocalVariables} f is only used for nested messages
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest.toObject = function(includeInstance, msg) {
  var f, obj = {
    username: jspb.Message.getFieldWithDefault(msg, 1, ""),
    password: jspb.Message.getFieldWithDefault(msg, 2, ""),
    firstname: jspb.Message.getFieldWithDefault(msg, 3, ""),
    lastname: jspb.Message.getFieldWithDefault(msg, 4, ""),
    additionaldescription: jspb.Message.getFieldWithDefault(msg, 5, "")
  };

  if (includeInstance) {
    obj.$jspbMessageInstance = msg;
  }
  return obj;
};
}


/**
 * Deserializes binary data (in protobuf wire format).
 * @param {jspb.ByteSource} bytes The bytes to deserialize.
 * @return {!proto.ru.miet.example.grpc.chat.service.RegisterRequest}
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest.deserializeBinary = function(bytes) {
  var reader = new jspb.BinaryReader(bytes);
  var msg = new proto.ru.miet.example.grpc.chat.service.RegisterRequest;
  return proto.ru.miet.example.grpc.chat.service.RegisterRequest.deserializeBinaryFromReader(msg, reader);
};


/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.ru.miet.example.grpc.chat.service.RegisterRequest} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.ru.miet.example.grpc.chat.service.RegisterRequest}
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest.deserializeBinaryFromReader = function(msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break;
    }
    var field = reader.getFieldNumber();
    switch (field) {
    case 1:
      var value = /** @type {string} */ (reader.readString());
      msg.setUsername(value);
      break;
    case 2:
      var value = /** @type {string} */ (reader.readString());
      msg.setPassword(value);
      break;
    case 3:
      var value = /** @type {string} */ (reader.readString());
      msg.setFirstname(value);
      break;
    case 4:
      var value = /** @type {string} */ (reader.readString());
      msg.setLastname(value);
      break;
    case 5:
      var value = /** @type {string} */ (reader.readString());
      msg.setAdditionaldescription(value);
      break;
    default:
      reader.skipField();
      break;
    }
  }
  return msg;
};


/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest.prototype.serializeBinary = function() {
  var writer = new jspb.BinaryWriter();
  proto.ru.miet.example.grpc.chat.service.RegisterRequest.serializeBinaryToWriter(this, writer);
  return writer.getResultBuffer();
};


/**
 * Serializes the given message to binary data (in protobuf wire
 * format), writing to the given BinaryWriter.
 * @param {!proto.ru.miet.example.grpc.chat.service.RegisterRequest} message
 * @param {!jspb.BinaryWriter} writer
 * @suppress {unusedLocalVariables} f is only used for nested messages
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest.serializeBinaryToWriter = function(message, writer) {
  var f = undefined;
  f = message.getUsername();
  if (f.length > 0) {
    writer.writeString(
      1,
      f
    );
  }
  f = message.getPassword();
  if (f.length > 0) {
    writer.writeString(
      2,
      f
    );
  }
  f = message.getFirstname();
  if (f.length > 0) {
    writer.writeString(
      3,
      f
    );
  }
  f = message.getLastname();
  if (f.length > 0) {
    writer.writeString(
      4,
      f
    );
  }
  f = message.getAdditionaldescription();
  if (f.length > 0) {
    writer.writeString(
      5,
      f
    );
  }
};


/**
 * optional string username = 1;
 * @return {string}
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest.prototype.getUsername = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 1, ""));
};


/**
 * @param {string} value
 * @return {!proto.ru.miet.example.grpc.chat.service.RegisterRequest} returns this
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest.prototype.setUsername = function(value) {
  return jspb.Message.setProto3StringField(this, 1, value);
};


/**
 * optional string password = 2;
 * @return {string}
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest.prototype.getPassword = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 2, ""));
};


/**
 * @param {string} value
 * @return {!proto.ru.miet.example.grpc.chat.service.RegisterRequest} returns this
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest.prototype.setPassword = function(value) {
  return jspb.Message.setProto3StringField(this, 2, value);
};


/**
 * optional string firstName = 3;
 * @return {string}
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest.prototype.getFirstname = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 3, ""));
};


/**
 * @param {string} value
 * @return {!proto.ru.miet.example.grpc.chat.service.RegisterRequest} returns this
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest.prototype.setFirstname = function(value) {
  return jspb.Message.setProto3StringField(this, 3, value);
};


/**
 * optional string lastName = 4;
 * @return {string}
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest.prototype.getLastname = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 4, ""));
};


/**
 * @param {string} value
 * @return {!proto.ru.miet.example.grpc.chat.service.RegisterRequest} returns this
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest.prototype.setLastname = function(value) {
  return jspb.Message.setProto3StringField(this, 4, value);
};


/**
 * optional string additionalDescription = 5;
 * @return {string}
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest.prototype.getAdditionaldescription = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 5, ""));
};


/**
 * @param {string} value
 * @return {!proto.ru.miet.example.grpc.chat.service.RegisterRequest} returns this
 */
proto.ru.miet.example.grpc.chat.service.RegisterRequest.prototype.setAdditionaldescription = function(value) {
  return jspb.Message.setProto3StringField(this, 5, value);
};





if (jspb.Message.GENERATE_TO_OBJECT) {
/**
 * Creates an object representation of this proto.
 * Field names that are reserved in JavaScript and will be renamed to pb_name.
 * Optional fields that are not set will be set to undefined.
 * To access a reserved field use, foo.pb_<name>, eg, foo.pb_default.
 * For the list of reserved names please see:
 *     net/proto2/compiler/js/internal/generator.cc#kKeyword.
 * @param {boolean=} opt_includeInstance Deprecated. whether to include the
 *     JSPB instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @return {!Object}
 */
proto.ru.miet.example.grpc.chat.service.RegisterResponse.prototype.toObject = function(opt_includeInstance) {
  return proto.ru.miet.example.grpc.chat.service.RegisterResponse.toObject(opt_includeInstance, this);
};


/**
 * Static version of the {@see toObject} method.
 * @param {boolean|undefined} includeInstance Deprecated. Whether to include
 *     the JSPB instance for transitional soy proto support:
 *     http://goto/soy-param-migration
 * @param {!proto.ru.miet.example.grpc.chat.service.RegisterResponse} msg The msg instance to transform.
 * @return {!Object}
 * @suppress {unusedLocalVariables} f is only used for nested messages
 */
proto.ru.miet.example.grpc.chat.service.RegisterResponse.toObject = function(includeInstance, msg) {
  var f, obj = {
    statuscode: jspb.Message.getFieldWithDefault(msg, 1, 0),
    statusdesc: jspb.Message.getFieldWithDefault(msg, 2, "")
  };

  if (includeInstance) {
    obj.$jspbMessageInstance = msg;
  }
  return obj;
};
}


/**
 * Deserializes binary data (in protobuf wire format).
 * @param {jspb.ByteSource} bytes The bytes to deserialize.
 * @return {!proto.ru.miet.example.grpc.chat.service.RegisterResponse}
 */
proto.ru.miet.example.grpc.chat.service.RegisterResponse.deserializeBinary = function(bytes) {
  var reader = new jspb.BinaryReader(bytes);
  var msg = new proto.ru.miet.example.grpc.chat.service.RegisterResponse;
  return proto.ru.miet.example.grpc.chat.service.RegisterResponse.deserializeBinaryFromReader(msg, reader);
};


/**
 * Deserializes binary data (in protobuf wire format) from the
 * given reader into the given message object.
 * @param {!proto.ru.miet.example.grpc.chat.service.RegisterResponse} msg The message object to deserialize into.
 * @param {!jspb.BinaryReader} reader The BinaryReader to use.
 * @return {!proto.ru.miet.example.grpc.chat.service.RegisterResponse}
 */
proto.ru.miet.example.grpc.chat.service.RegisterResponse.deserializeBinaryFromReader = function(msg, reader) {
  while (reader.nextField()) {
    if (reader.isEndGroup()) {
      break;
    }
    var field = reader.getFieldNumber();
    switch (field) {
    case 1:
      var value = /** @type {!proto.ru.miet.example.grpc.chat.service.StatusCode} */ (reader.readEnum());
      msg.setStatuscode(value);
      break;
    case 2:
      var value = /** @type {string} */ (reader.readString());
      msg.setStatusdesc(value);
      break;
    default:
      reader.skipField();
      break;
    }
  }
  return msg;
};


/**
 * Serializes the message to binary data (in protobuf wire format).
 * @return {!Uint8Array}
 */
proto.ru.miet.example.grpc.chat.service.RegisterResponse.prototype.serializeBinary = function() {
  var writer = new jspb.BinaryWriter();
  proto.ru.miet.example.grpc.chat.service.RegisterResponse.serializeBinaryToWriter(this, writer);
  return writer.getResultBuffer();
};


/**
 * Serializes the given message to binary data (in protobuf wire
 * format), writing to the given BinaryWriter.
 * @param {!proto.ru.miet.example.grpc.chat.service.RegisterResponse} message
 * @param {!jspb.BinaryWriter} writer
 * @suppress {unusedLocalVariables} f is only used for nested messages
 */
proto.ru.miet.example.grpc.chat.service.RegisterResponse.serializeBinaryToWriter = function(message, writer) {
  var f = undefined;
  f = message.getStatuscode();
  if (f !== 0.0) {
    writer.writeEnum(
      1,
      f
    );
  }
  f = message.getStatusdesc();
  if (f.length > 0) {
    writer.writeString(
      2,
      f
    );
  }
};


/**
 * optional StatusCode statusCode = 1;
 * @return {!proto.ru.miet.example.grpc.chat.service.StatusCode}
 */
proto.ru.miet.example.grpc.chat.service.RegisterResponse.prototype.getStatuscode = function() {
  return /** @type {!proto.ru.miet.example.grpc.chat.service.StatusCode} */ (jspb.Message.getFieldWithDefault(this, 1, 0));
};


/**
 * @param {!proto.ru.miet.example.grpc.chat.service.StatusCode} value
 * @return {!proto.ru.miet.example.grpc.chat.service.RegisterResponse} returns this
 */
proto.ru.miet.example.grpc.chat.service.RegisterResponse.prototype.setStatuscode = function(value) {
  return jspb.Message.setProto3EnumField(this, 1, value);
};


/**
 * optional string statusDesc = 2;
 * @return {string}
 */
proto.ru.miet.example.grpc.chat.service.RegisterResponse.prototype.getStatusdesc = function() {
  return /** @type {string} */ (jspb.Message.getFieldWithDefault(this, 2, ""));
};


/**
 * @param {string} value
 * @return {!proto.ru.miet.example.grpc.chat.service.RegisterResponse} returns this
 */
proto.ru.miet.example.grpc.chat.service.RegisterResponse.prototype.setStatusdesc = function(value) {
  return jspb.Message.setProto3StringField(this, 2, value);
};


goog.object.extend(exports, proto.ru.miet.example.grpc.chat.service);

module.exports = proto.ru.miet.example.grpc.chat.service;
