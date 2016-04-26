
/**
 * LogisticsServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.haoys.logisticsserver.ws.service;

    /**
     *  LogisticsServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class LogisticsServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public LogisticsServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public LogisticsServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getElseLeakageOfSingle method
            * override this method for handling normal response from getElseLeakageOfSingle operation
            */
           public void receiveResultgetElseLeakageOfSingle(
                    com.haoys.logisticsserver.ws.service.LogisticsServiceStub.GetElseLeakageOfSingleResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getElseLeakageOfSingle operation
           */
            public void receiveErrorgetElseLeakageOfSingle(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for queryOverLogisticsDetail method
            * override this method for handling normal response from queryOverLogisticsDetail operation
            */
           public void receiveResultqueryOverLogisticsDetail(
                    com.haoys.logisticsserver.ws.service.LogisticsServiceStub.QueryOverLogisticsDetailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from queryOverLogisticsDetail operation
           */
            public void receiveErrorqueryOverLogisticsDetail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getLeakageOfSingle method
            * override this method for handling normal response from getLeakageOfSingle operation
            */
           public void receiveResultgetLeakageOfSingle(
                    com.haoys.logisticsserver.ws.service.LogisticsServiceStub.GetLeakageOfSingleResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getLeakageOfSingle operation
           */
            public void receiveErrorgetLeakageOfSingle(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for queryLogisticsDetail method
            * override this method for handling normal response from queryLogisticsDetail operation
            */
           public void receiveResultqueryLogisticsDetail(
                    com.haoys.logisticsserver.ws.service.LogisticsServiceStub.QueryLogisticsDetailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from queryLogisticsDetail operation
           */
            public void receiveErrorqueryLogisticsDetail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getLogisticsInfoByID method
            * override this method for handling normal response from getLogisticsInfoByID operation
            */
           public void receiveResultgetLogisticsInfoByID(
                    com.haoys.logisticsserver.ws.service.LogisticsServiceStub.GetLogisticsInfoByIDResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getLogisticsInfoByID operation
           */
            public void receiveErrorgetLogisticsInfoByID(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for putMemcached method
            * override this method for handling normal response from putMemcached operation
            */
           public void receiveResultputMemcached(
                    com.haoys.logisticsserver.ws.service.LogisticsServiceStub.PutMemcachedResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from putMemcached operation
           */
            public void receiveErrorputMemcached(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getElseExpressesTrackNum method
            * override this method for handling normal response from getElseExpressesTrackNum operation
            */
           public void receiveResultgetElseExpressesTrackNum(
                    com.haoys.logisticsserver.ws.service.LogisticsServiceStub.GetElseExpressesTrackNumResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getElseExpressesTrackNum operation
           */
            public void receiveErrorgetElseExpressesTrackNum(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getTrackNumberBySource method
            * override this method for handling normal response from getTrackNumberBySource operation
            */
           public void receiveResultgetTrackNumberBySource(
                    com.haoys.logisticsserver.ws.service.LogisticsServiceStub.GetTrackNumberBySourceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getTrackNumberBySource operation
           */
            public void receiveErrorgetTrackNumberBySource(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for queryAnOverLogisticsDetail method
            * override this method for handling normal response from queryAnOverLogisticsDetail operation
            */
           public void receiveResultqueryAnOverLogisticsDetail(
                    com.haoys.logisticsserver.ws.service.LogisticsServiceStub.QueryAnOverLogisticsDetailResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from queryAnOverLogisticsDetail operation
           */
            public void receiveErrorqueryAnOverLogisticsDetail(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for removeLastMaxId method
            * override this method for handling normal response from removeLastMaxId operation
            */
           public void receiveResultremoveLastMaxId(
                    com.haoys.logisticsserver.ws.service.LogisticsServiceStub.RemoveLastMaxIdResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from removeLastMaxId operation
           */
            public void receiveErrorremoveLastMaxId(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for putInfoToDB method
            * override this method for handling normal response from putInfoToDB operation
            */
           public void receiveResultputInfoToDB(
                    com.haoys.logisticsserver.ws.service.LogisticsServiceStub.PutInfoToDBResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from putInfoToDB operation
           */
            public void receiveErrorputInfoToDB(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getMaxID method
            * override this method for handling normal response from getMaxID operation
            */
           public void receiveResultgetMaxID(
                    com.haoys.logisticsserver.ws.service.LogisticsServiceStub.GetMaxIDResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getMaxID operation
           */
            public void receiveErrorgetMaxID(java.lang.Exception e) {
            }
                


    }
    