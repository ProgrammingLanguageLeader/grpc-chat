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
proto.ru.miet.example.grpc.chat.service = require('./AuthService_pb.js');

/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?Object} options
 * @constructor
 * @struct
 * @final
 */
proto.ru.miet.example.grpc.chat.service.AuthServiceClient =
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
proto.ru.miet.example.grpc.chat.service.AuthServicePromiseClient =
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
 * @type {!grpc.web.MethodDescriptor
 *   !proto.ru.miet.example.grpc.chat.service.LoginRequest,
 *   !proto.ru.miet.example.grpc.chat.service.LoginResponse>}
 */
const methodDescriptor_AuthService_login = new grpc.web.MethodDescriptor(
  '/ru.miet.example.grpc.chat.service.AuthService/login',
  grpc.web.MethodType.UNARY,
  proto.ru.miet.example.grpc.chat.service.LoginRequest,
  proto.ru.miet.example.grpc.chat.service.LoginResponse,
  /**
   * @param {!proto.ru.miet.example.grpc.chat.service.LoginRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.ru.miet.example.grpc.chat.service.LoginResponse.deserializeBinary
);


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.ru.miet.example.grpc.chat.service.LoginRequest,
 *   !proto.ru.miet.example.grpc.chat.service.LoginResponse>}
 */
const methodInfo_AuthService_login = new grpc.web.AbstractClientBase.MethodInfo(
  proto.ru.miet.example.grpc.chat.service.LoginResponse,
  /**
   * @param {!proto.ru.miet.example.grpc.chat.service.LoginRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.ru.miet.example.grpc.chat.service.LoginResponse.deserializeBinary
);


/**
 * @param {!proto.ru.miet.example.grpc.chat.service.LoginRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.ru.miet.example.grpc.chat.service.LoginResponse)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.ru.miet.example.grpc.chat.service.LoginResponse>|undefined}
 *     The XHR Node Readable Stream
 */
proto.ru.miet.example.grpc.chat.service.AuthServiceClient.prototype.login =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/ru.miet.example.grpc.chat.service.AuthService/login',
      request,
      metadata || {},
      methodDescriptor_AuthService_login,
      callback);
};


/**
 * @param {!proto.ru.miet.example.grpc.chat.service.LoginRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.ru.miet.example.grpc.chat.service.LoginResponse>}
 *     Promise that resolves to the response
 */
proto.ru.miet.example.grpc.chat.service.AuthServicePromiseClient.prototype.login =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/ru.miet.example.grpc.chat.service.AuthService/login',
      request,
      metadata || {},
      methodDescriptor_AuthService_login);
};


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.ru.miet.example.grpc.chat.service.RefreshTokenRequest,
 *   !proto.ru.miet.example.grpc.chat.service.RefreshTokenResponse>}
 */
const methodDescriptor_AuthService_refreshToken = new grpc.web.MethodDescriptor(
  '/ru.miet.example.grpc.chat.service.AuthService/refreshToken',
  grpc.web.MethodType.UNARY,
  proto.ru.miet.example.grpc.chat.service.RefreshTokenRequest,
  proto.ru.miet.example.grpc.chat.service.RefreshTokenResponse,
  /**
   * @param {!proto.ru.miet.example.grpc.chat.service.RefreshTokenRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.ru.miet.example.grpc.chat.service.RefreshTokenResponse.deserializeBinary
);


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.ru.miet.example.grpc.chat.service.RefreshTokenRequest,
 *   !proto.ru.miet.example.grpc.chat.service.RefreshTokenResponse>}
 */
const methodInfo_AuthService_refreshToken = new grpc.web.AbstractClientBase.MethodInfo(
  proto.ru.miet.example.grpc.chat.service.RefreshTokenResponse,
  /**
   * @param {!proto.ru.miet.example.grpc.chat.service.RefreshTokenRequest} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.ru.miet.example.grpc.chat.service.RefreshTokenResponse.deserializeBinary
);


/**
 * @param {!proto.ru.miet.example.grpc.chat.service.RefreshTokenRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.ru.miet.example.grpc.chat.service.RefreshTokenResponse)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.ru.miet.example.grpc.chat.service.RefreshTokenResponse>|undefined}
 *     The XHR Node Readable Stream
 */
proto.ru.miet.example.grpc.chat.service.AuthServiceClient.prototype.refreshToken =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/ru.miet.example.grpc.chat.service.AuthService/refreshToken',
      request,
      metadata || {},
      methodDescriptor_AuthService_refreshToken,
      callback);
};


/**
 * @param {!proto.ru.miet.example.grpc.chat.service.RefreshTokenRequest} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.ru.miet.example.grpc.chat.service.RefreshTokenResponse>}
 *     Promise that resolves to the response
 */
proto.ru.miet.example.grpc.chat.service.AuthServicePromiseClient.prototype.refreshToken =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/ru.miet.example.grpc.chat.service.AuthService/refreshToken',
      request,
      metadata || {},
      methodDescriptor_AuthService_refreshToken);
};


module.exports = proto.ru.miet.example.grpc.chat.service;

