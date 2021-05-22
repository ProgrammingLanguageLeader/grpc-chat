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


var common_pb = require('./common_pb.js')
const proto = {};
proto.ru = {};
proto.ru.miet = {};
proto.ru.miet.example = {};
proto.ru.miet.example.grpc = {};
proto.ru.miet.example.grpc.chat = {};
proto.ru.miet.example.grpc.chat.service = require('./ChatService_pb.js');

/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?Object} options
 * @constructor
 * @struct
 * @final
 */
proto.ru.miet.example.grpc.chat.service.ChatServiceClient =
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
proto.ru.miet.example.grpc.chat.service.ChatServicePromiseClient =
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
 *   !proto.ru.miet.example.grpc.chat.service.GetChatsRequest,
 *   !proto.ru.miet.example.grpc.chat.service.GetChatsResponse>}
 */
const methodDescriptor_ChatService_getChats = new grpc.web.MethodDescriptor(
  '/ru.miet.example.grpc.chat.service.ChatService/getChats',
  grpc.web.MethodType.UNARY,
  proto.ru.miet.example.grpc.chat.service.GetChatsRequest,
  proto.ru.miet.example.grpc.chat.service.GetChatsResponse,
  /**
   * @param {!proto.ru.miet.example.grpc.chat.service.GetChatsRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.ru.miet.example.grpc.chat.service.GetChatsResponse.deserializeBinary
);


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.ru.miet.example.grpc.chat.service.GetChatsRequest,
 *   !proto.ru.miet.example.grpc.chat.service.GetChatsResponse>}
 */
const methodInfo_ChatService_getChats = new grpc.web.AbstractClientBase.MethodInfo(
  proto.ru.miet.example.grpc.chat.service.GetChatsResponse,
  /**
   * @param {!proto.ru.miet.example.grpc.chat.service.GetChatsRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.ru.miet.example.grpc.chat.service.GetChatsResponse.deserializeBinary
);


/**
 * @param {!proto.ru.miet.example.grpc.chat.service.GetChatsRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.ru.miet.example.grpc.chat.service.GetChatsResponse)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.ru.miet.example.grpc.chat.service.GetChatsResponse>|undefined}
 *     The XHR Node Readable Stream
 */
proto.ru.miet.example.grpc.chat.service.ChatServiceClient.prototype.getChats =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/ru.miet.example.grpc.chat.service.ChatService/getChats',
      request,
      metadata || {},
      methodDescriptor_ChatService_getChats,
      callback);
};


/**
 * @param {!proto.ru.miet.example.grpc.chat.service.GetChatsRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.ru.miet.example.grpc.chat.service.GetChatsResponse>}
 *     Promise that resolves to the response
 */
proto.ru.miet.example.grpc.chat.service.ChatServicePromiseClient.prototype.getChats =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/ru.miet.example.grpc.chat.service.ChatService/getChats',
      request,
      metadata || {},
      methodDescriptor_ChatService_getChats);
};


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.ru.miet.example.grpc.chat.service.GetMembersRequest,
 *   !proto.ru.miet.example.grpc.chat.service.GetMembersResponse>}
 */
const methodDescriptor_ChatService_getMembers = new grpc.web.MethodDescriptor(
  '/ru.miet.example.grpc.chat.service.ChatService/getMembers',
  grpc.web.MethodType.UNARY,
  proto.ru.miet.example.grpc.chat.service.GetMembersRequest,
  proto.ru.miet.example.grpc.chat.service.GetMembersResponse,
  /**
   * @param {!proto.ru.miet.example.grpc.chat.service.GetMembersRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.ru.miet.example.grpc.chat.service.GetMembersResponse.deserializeBinary
);


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.ru.miet.example.grpc.chat.service.GetMembersRequest,
 *   !proto.ru.miet.example.grpc.chat.service.GetMembersResponse>}
 */
const methodInfo_ChatService_getMembers = new grpc.web.AbstractClientBase.MethodInfo(
  proto.ru.miet.example.grpc.chat.service.GetMembersResponse,
  /**
   * @param {!proto.ru.miet.example.grpc.chat.service.GetMembersRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.ru.miet.example.grpc.chat.service.GetMembersResponse.deserializeBinary
);


/**
 * @param {!proto.ru.miet.example.grpc.chat.service.GetMembersRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.ru.miet.example.grpc.chat.service.GetMembersResponse)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.ru.miet.example.grpc.chat.service.GetMembersResponse>|undefined}
 *     The XHR Node Readable Stream
 */
proto.ru.miet.example.grpc.chat.service.ChatServiceClient.prototype.getMembers =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/ru.miet.example.grpc.chat.service.ChatService/getMembers',
      request,
      metadata || {},
      methodDescriptor_ChatService_getMembers,
      callback);
};


/**
 * @param {!proto.ru.miet.example.grpc.chat.service.GetMembersRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.ru.miet.example.grpc.chat.service.GetMembersResponse>}
 *     Promise that resolves to the response
 */
proto.ru.miet.example.grpc.chat.service.ChatServicePromiseClient.prototype.getMembers =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/ru.miet.example.grpc.chat.service.ChatService/getMembers',
      request,
      metadata || {},
      methodDescriptor_ChatService_getMembers);
};


module.exports = proto.ru.miet.example.grpc.chat.service;

