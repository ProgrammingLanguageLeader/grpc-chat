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
proto.ru.miet.example.grpc.chat.service = require('./UserSearchService_pb.js');

/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?Object} options
 * @constructor
 * @struct
 * @final
 */
proto.ru.miet.example.grpc.chat.service.UserSearchServiceClient =
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
proto.ru.miet.example.grpc.chat.service.UserSearchServicePromiseClient =
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
 *   !proto.ru.miet.example.grpc.chat.service.UserSearchRequest,
 *   !proto.ru.miet.example.grpc.chat.service.UserSearchResponse>}
 */
const methodDescriptor_UserSearchService_search = new grpc.web.MethodDescriptor(
  '/ru.miet.example.grpc.chat.service.UserSearchService/search',
  grpc.web.MethodType.UNARY,
  proto.ru.miet.example.grpc.chat.service.UserSearchRequest,
  proto.ru.miet.example.grpc.chat.service.UserSearchResponse,
  /**
   * @param {!proto.ru.miet.example.grpc.chat.service.UserSearchRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.ru.miet.example.grpc.chat.service.UserSearchResponse.deserializeBinary
);


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.ru.miet.example.grpc.chat.service.UserSearchRequest,
 *   !proto.ru.miet.example.grpc.chat.service.UserSearchResponse>}
 */
const methodInfo_UserSearchService_search = new grpc.web.AbstractClientBase.MethodInfo(
  proto.ru.miet.example.grpc.chat.service.UserSearchResponse,
  /**
   * @param {!proto.ru.miet.example.grpc.chat.service.UserSearchRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.ru.miet.example.grpc.chat.service.UserSearchResponse.deserializeBinary
);


/**
 * @param {!proto.ru.miet.example.grpc.chat.service.UserSearchRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.ru.miet.example.grpc.chat.service.UserSearchResponse)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.ru.miet.example.grpc.chat.service.UserSearchResponse>|undefined}
 *     The XHR Node Readable Stream
 */
proto.ru.miet.example.grpc.chat.service.UserSearchServiceClient.prototype.search =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/ru.miet.example.grpc.chat.service.UserSearchService/search',
      request,
      metadata || {},
      methodDescriptor_UserSearchService_search,
      callback);
};


/**
 * @param {!proto.ru.miet.example.grpc.chat.service.UserSearchRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.ru.miet.example.grpc.chat.service.UserSearchResponse>}
 *     Promise that resolves to the response
 */
proto.ru.miet.example.grpc.chat.service.UserSearchServicePromiseClient.prototype.search =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/ru.miet.example.grpc.chat.service.UserSearchService/search',
      request,
      metadata || {},
      methodDescriptor_UserSearchService_search);
};


module.exports = proto.ru.miet.example.grpc.chat.service;

