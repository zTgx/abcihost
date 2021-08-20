;;;----------------------------------------------------------------------------------
;;; Generated by protoc-gen-clojure.  DO NOT EDIT
;;;
;;; Message Implementation of package tendermint.version
;;;----------------------------------------------------------------------------------
(ns tendermint.version
  (:require [protojure.protobuf.protocol :as pb]
            [protojure.protobuf.serdes.core :as serdes.core]
            [protojure.protobuf.serdes.complex :as serdes.complex]
            [protojure.protobuf.serdes.utils :refer [tag-map]]
            [protojure.protobuf.serdes.stream :as serdes.stream]
            [com.google.protobuf :as com.google.protobuf]
            [clojure.set :as set]
            [clojure.spec.alpha :as s]))

;;----------------------------------------------------------------------------------
;;----------------------------------------------------------------------------------
;; Forward declarations
;;----------------------------------------------------------------------------------
;;----------------------------------------------------------------------------------

(declare cis->Consensus)
(declare ecis->Consensus)
(declare new-Consensus)


;;----------------------------------------------------------------------------------
;;----------------------------------------------------------------------------------
;; Message Implementations
;;----------------------------------------------------------------------------------
;;----------------------------------------------------------------------------------

;-----------------------------------------------------------------------------
; Consensus
;-----------------------------------------------------------------------------
(defrecord Consensus-record [block app]
  pb/Writer
  (serialize [this os]
    (serdes.core/write-UInt64 1  {:optimize true} (:block this) os)
    (serdes.core/write-UInt64 2  {:optimize true} (:app this) os))
  pb/TypeReflection
  (gettype [this]
    "tendermint.version.Consensus"))

(s/def :tendermint.version.Consensus/block int?)
(s/def :tendermint.version.Consensus/app int?)
(s/def ::Consensus-spec (s/keys :opt-un [:tendermint.version.Consensus/block :tendermint.version.Consensus/app ]))
(def Consensus-defaults {:block 0 :app 0 })

(defn cis->Consensus
  "CodedInputStream to Consensus"
  [is]
  (->> (tag-map Consensus-defaults
         (fn [tag index]
             (case index
               1 [:block (serdes.core/cis->UInt64 is)]
               2 [:app (serdes.core/cis->UInt64 is)]

               [index (serdes.core/cis->undefined tag is)]))
         is)
        (map->Consensus-record)))

(defn ecis->Consensus
  "Embedded CodedInputStream to Consensus"
  [is]
  (serdes.core/cis->embedded cis->Consensus is))

(defn new-Consensus
  "Creates a new instance from a map, similar to map->Consensus except that
  it properly accounts for nested messages, when applicable.
  "
  [init]
  {:pre [(if (s/valid? ::Consensus-spec init) true (throw (ex-info "Invalid input" (s/explain-data ::Consensus-spec init))))]}
  (-> (merge Consensus-defaults init)
      (map->Consensus-record)))

(defn pb->Consensus
  "Protobuf to Consensus"
  [input]
  (cis->Consensus (serdes.stream/new-cis input)))

(def ^:protojure.protobuf.any/record Consensus-meta {:type "tendermint.version.Consensus" :decoder pb->Consensus})

