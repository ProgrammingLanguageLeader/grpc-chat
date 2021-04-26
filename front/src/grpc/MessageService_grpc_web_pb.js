/**
 * @fileoverview gRPC-Web generated client stub for ru.miet.example.grpc.chat.service
 * @enhanceable
 * @public
 */

// GENERATED CODE -- DO NOT EDIT!


/* eslint-disable */
// @ts-nocheck



const grpc = {};
grpc.web = require('grpc-web');


var google_protobuf_timestamp_pb = require('google-protobuf/google/protobuf/timestamp_pb.js')

var common_pb = require('./common_pb.js')
const proto = {};
proto.ru = {};
proto.ru.miet = {};
proto.ru.miet.example = {};
proto.ru.miet.example.grpc = {};
proto.ru.miet.example.grpc.chat = {};
proto.ru.miet.example.grpc.chat.service = require('./MessageService_pb.js');

/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?Object} options
 * @constructor
 * @struct
 * @final
 */
proto.ru.miet.example.grpc.chat.service.MessageServiceClient =
    function(hostname, credentials, options) {
  if (!options) options = {};
  options['format'] = 'text';

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname;

};


/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?Object} options
 * @constructor
 * @struct
 * @final
 */
proto.ru.miet.example.grpc.chat.service.MessageServicePromiseClient =
    function(hostname, credentials, options) {
  if (!options) options = {};
  options['format'] = 'text';

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname;

};


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.ru.miet.example.grpc.chat.service.SendMessageRequest,
 *   !proto.ru.miet.example.grpc.chat.service.SendMessageResponse>}
 */
const methodDescriptor_MessageService_send = new grpc.web.MethodDescriptor(
  '/ru.miet.example.grpc.chat.service.MessageService/send',
  grpc.web.MethodType.UNARY,
  proto.ru.miet.example.grpc.chat.service.SendMessageRequest,
  proto.ru.miet.example.grpc.chat.service.SendMessageResponse,
  /**
   * @param {!proto.ru.miet.example.grpc.chat.service.SendMessageRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.ru.miet.example.grpc.chat.service.SendMessageResponse.deserializeBinary
);


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.ru.miet.example.grpc.chat.service.SendMessageRequest,
 *   !proto.ru.miet.example.grpc.chat.service.SendMessageResponse>}
 */
const methodInfo_MessageService_send = new grpc.web.AbstractClientBase.MethodInfo(
  proto.ru.miet.example.grpc.chat.service.SendMessageResponse,
  /**
   * @param {!proto.ru.miet.example.grpc.chat.service.SendMessageRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.ru.miet.example.grpc.chat.service.SendMessageResponse.deserializeBinary
);


/**
 * @param {!proto.ru.miet.example.grpc.chat.service.SendMessageRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.ru.miet.example.grpc.chat.service.SendMessageResponse)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.ru.miet.example.grpc.chat.service.SendMessageResponse>|undefined}
 *     The XHR Node Readable Stream
 */
proto.ru.miet.example.grpc.chat.service.MessageServiceClient.prototype.send =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/ru.miet.example.grpc.chat.service.MessageService/send',
      request,
      metadata || {},
      methodDescriptor_MessageService_send,
      callback);
};


/**
 * @param {!proto.ru.miet.example.grpc.chat.service.SendMessageRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.ru.miet.example.grpc.chat.service.SendMessageResponse>}
 *     Promise that resolves to the response
 */
proto.ru.miet.example.grpc.chat.service.MessageServicePromiseClient.prototype.send =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/ru.miet.example.grpc.chat.service.MessageService/send',
      request,
      metadata || {},
      methodDescriptor_MessageService_send);
};


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.ru.miet.example.grpc.chat.service.GetMessageRequest,
 *   !proto.ru.miet.example.grpc.chat.service.GetMessageResponse>}
 */
const methodDescriptor_MessageService_get = new grpc.web.MethodDescriptor(
  '/ru.miet.example.grpc.chat.service.MessageService/get',
  grpc.web.MethodType.UNARY,
  proto.ru.miet.example.grpc.chat.service.GetMessageRequest,
  proto.ru.miet.example.grpc.chat.service.GetMessageResponse,
  /**
   * @param {!proto.ru.miet.example.grpc.chat.service.GetMessageRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.ru.miet.example.grpc.chat.service.GetMessageResponse.deserializeBinary
);


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.ru.miet.example.grpc.chat.service.GetMessageRequest,
 *   !proto.ru.miet.example.grpc.chat.service.GetMessageResponse>}
 */
const methodInfo_MessageService_get = new grpc.web.AbstractClientBase.MethodInfo(
  proto.ru.miet.example.grpc.chat.service.GetMessageResponse,
  /**
   * @param {!proto.ru.miet.example.grpc.chat.service.GetMessageRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.ru.miet.example.grpc.chat.service.GetMessageResponse.deserializeBinary
);


/**
 * @param {!proto.ru.miet.example.grpc.chat.service.GetMessageRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.ru.miet.example.grpc.chat.service.GetMessageResponse)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.ru.miet.example.grpc.chat.service.GetMessageResponse>|undefined}
 *     The XHR Node Readable Stream
 */
proto.ru.miet.example.grpc.chat.service.MessageServiceClient.prototype.get =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/ru.miet.example.grpc.chat.service.MessageService/get',
      request,
      metadata || {},
      methodDescriptor_MessageService_get,
      callback);
};


/**
 * @param {!proto.ru.miet.example.grpc.chat.service.GetMessageRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.ru.miet.example.grpc.chat.service.GetMessageResponse>}
 *     Promise that resolves to the response
 */
proto.ru.miet.example.grpc.chat.service.MessageServicePromiseClient.prototype.get =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/ru.miet.example.grpc.chat.service.MessageService/get',
      request,
      metadata || {},
      methodDescriptor_MessageService_get);
};


module.exports = proto.ru.miet.example.grpc.chat.service;

