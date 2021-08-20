;;;----------------------------------------------------------------------------------
;;; Generated by protoc-gen-clojure.  DO NOT EDIT
;;;
;;; Message Implementation of package tendermint.crypto
;;;----------------------------------------------------------------------------------
(ns tendermint.crypto
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

(declare cis->Proof)
(declare ecis->Proof)
(declare new-Proof)
(declare cis->ValueOp)
(declare ecis->ValueOp)
(declare new-ValueOp)
(declare cis->DominoOp)
(declare ecis->DominoOp)
(declare new-DominoOp)
(declare cis->ProofOp)
(declare ecis->ProofOp)
(declare new-ProofOp)
(declare cis->ProofOps)
(declare ecis->ProofOps)
(declare new-ProofOps)
(declare cis->PublicKey)
(declare ecis->PublicKey)
(declare new-PublicKey)

;;----------------------------------------------------------------------------------
;;----------------------------------------------------------------------------------
;; PublicKey-sum's oneof Implementations
;;----------------------------------------------------------------------------------
;;----------------------------------------------------------------------------------

(defn convert-PublicKey-sum [origkeyval]
  (cond
     (get-in origkeyval [:sum :ed25519]) origkeyval
     (get-in origkeyval [:sum :secp256k1]) origkeyval
     (get-in origkeyval [:sum :sr25519]) origkeyval
     :default origkeyval))

(defn write-PublicKey-sum [sum os]
  (let [field (first sum)
        k (when-not (nil? field) (key field))
        v (when-not (nil? field) (val field))]
     (case k
         :ed25519 (serdes.core/write-Bytes 1  {:optimize false} v os)
         :secp256k1 (serdes.core/write-Bytes 2  {:optimize false} v os)
         :sr25519 (serdes.core/write-Bytes 3  {:optimize false} v os)
         nil)))



;;----------------------------------------------------------------------------------
;;----------------------------------------------------------------------------------
;; Message Implementations
;;----------------------------------------------------------------------------------
;;----------------------------------------------------------------------------------

;-----------------------------------------------------------------------------
; Proof
;-----------------------------------------------------------------------------
(defrecord Proof-record [total index leaf-hash aunts]
  pb/Writer
  (serialize [this os]
    (serdes.core/write-Int64 1  {:optimize true} (:total this) os)
    (serdes.core/write-Int64 2  {:optimize true} (:index this) os)
    (serdes.core/write-Bytes 3  {:optimize true} (:leaf-hash this) os)
    (serdes.complex/write-repeated serdes.core/write-Bytes 4 (:aunts this) os))
  pb/TypeReflection
  (gettype [this]
    "tendermint.crypto.Proof"))

(s/def :tendermint.crypto.Proof/total int?)
(s/def :tendermint.crypto.Proof/index int?)
(s/def :tendermint.crypto.Proof/leaf-hash bytes?)
(s/def :tendermint.crypto.Proof/aunts (s/every bytes?))
(s/def ::Proof-spec (s/keys :opt-un [:tendermint.crypto.Proof/total :tendermint.crypto.Proof/index :tendermint.crypto.Proof/leaf-hash :tendermint.crypto.Proof/aunts ]))
(def Proof-defaults {:total 0 :index 0 :leaf-hash (byte-array 0) :aunts [] })

(defn cis->Proof
  "CodedInputStream to Proof"
  [is]
  (->> (tag-map Proof-defaults
         (fn [tag index]
             (case index
               1 [:total (serdes.core/cis->Int64 is)]
               2 [:index (serdes.core/cis->Int64 is)]
               3 [:leaf-hash (serdes.core/cis->Bytes is)]
               4 [:aunts (serdes.complex/cis->repeated serdes.core/cis->Bytes is)]

               [index (serdes.core/cis->undefined tag is)]))
         is)
        (map->Proof-record)))

(defn ecis->Proof
  "Embedded CodedInputStream to Proof"
  [is]
  (serdes.core/cis->embedded cis->Proof is))

(defn new-Proof
  "Creates a new instance from a map, similar to map->Proof except that
  it properly accounts for nested messages, when applicable.
  "
  [init]
  {:pre [(if (s/valid? ::Proof-spec init) true (throw (ex-info "Invalid input" (s/explain-data ::Proof-spec init))))]}
  (-> (merge Proof-defaults init)
      (map->Proof-record)))

(defn pb->Proof
  "Protobuf to Proof"
  [input]
  (cis->Proof (serdes.stream/new-cis input)))

(def ^:protojure.protobuf.any/record Proof-meta {:type "tendermint.crypto.Proof" :decoder pb->Proof})

;-----------------------------------------------------------------------------
; ValueOp
;-----------------------------------------------------------------------------
(defrecord ValueOp-record [key proof]
  pb/Writer
  (serialize [this os]
    (serdes.core/write-Bytes 1  {:optimize true} (:key this) os)
    (serdes.core/write-embedded 2 (:proof this) os))
  pb/TypeReflection
  (gettype [this]
    "tendermint.crypto.ValueOp"))

(s/def :tendermint.crypto.ValueOp/key bytes?)

(s/def ::ValueOp-spec (s/keys :opt-un [:tendermint.crypto.ValueOp/key ]))
(def ValueOp-defaults {:key (byte-array 0) })

(defn cis->ValueOp
  "CodedInputStream to ValueOp"
  [is]
  (->> (tag-map ValueOp-defaults
         (fn [tag index]
             (case index
               1 [:key (serdes.core/cis->Bytes is)]
               2 [:proof (ecis->Proof is)]

               [index (serdes.core/cis->undefined tag is)]))
         is)
        (map->ValueOp-record)))

(defn ecis->ValueOp
  "Embedded CodedInputStream to ValueOp"
  [is]
  (serdes.core/cis->embedded cis->ValueOp is))

(defn new-ValueOp
  "Creates a new instance from a map, similar to map->ValueOp except that
  it properly accounts for nested messages, when applicable.
  "
  [init]
  {:pre [(if (s/valid? ::ValueOp-spec init) true (throw (ex-info "Invalid input" (s/explain-data ::ValueOp-spec init))))]}
  (-> (merge ValueOp-defaults init)
      (cond-> (some? (get init :proof)) (update :proof new-Proof))
      (map->ValueOp-record)))

(defn pb->ValueOp
  "Protobuf to ValueOp"
  [input]
  (cis->ValueOp (serdes.stream/new-cis input)))

(def ^:protojure.protobuf.any/record ValueOp-meta {:type "tendermint.crypto.ValueOp" :decoder pb->ValueOp})

;-----------------------------------------------------------------------------
; DominoOp
;-----------------------------------------------------------------------------
(defrecord DominoOp-record [key input output]
  pb/Writer
  (serialize [this os]
    (serdes.core/write-String 1  {:optimize true} (:key this) os)
    (serdes.core/write-String 2  {:optimize true} (:input this) os)
    (serdes.core/write-String 3  {:optimize true} (:output this) os))
  pb/TypeReflection
  (gettype [this]
    "tendermint.crypto.DominoOp"))

(s/def :tendermint.crypto.DominoOp/key string?)
(s/def :tendermint.crypto.DominoOp/input string?)
(s/def :tendermint.crypto.DominoOp/output string?)
(s/def ::DominoOp-spec (s/keys :opt-un [:tendermint.crypto.DominoOp/key :tendermint.crypto.DominoOp/input :tendermint.crypto.DominoOp/output ]))
(def DominoOp-defaults {:key "" :input "" :output "" })

(defn cis->DominoOp
  "CodedInputStream to DominoOp"
  [is]
  (->> (tag-map DominoOp-defaults
         (fn [tag index]
             (case index
               1 [:key (serdes.core/cis->String is)]
               2 [:input (serdes.core/cis->String is)]
               3 [:output (serdes.core/cis->String is)]

               [index (serdes.core/cis->undefined tag is)]))
         is)
        (map->DominoOp-record)))

(defn ecis->DominoOp
  "Embedded CodedInputStream to DominoOp"
  [is]
  (serdes.core/cis->embedded cis->DominoOp is))

(defn new-DominoOp
  "Creates a new instance from a map, similar to map->DominoOp except that
  it properly accounts for nested messages, when applicable.
  "
  [init]
  {:pre [(if (s/valid? ::DominoOp-spec init) true (throw (ex-info "Invalid input" (s/explain-data ::DominoOp-spec init))))]}
  (-> (merge DominoOp-defaults init)
      (map->DominoOp-record)))

(defn pb->DominoOp
  "Protobuf to DominoOp"
  [input]
  (cis->DominoOp (serdes.stream/new-cis input)))

(def ^:protojure.protobuf.any/record DominoOp-meta {:type "tendermint.crypto.DominoOp" :decoder pb->DominoOp})

;-----------------------------------------------------------------------------
; ProofOp
;-----------------------------------------------------------------------------
(defrecord ProofOp-record [type key data]
  pb/Writer
  (serialize [this os]
    (serdes.core/write-String 1  {:optimize true} (:type this) os)
    (serdes.core/write-Bytes 2  {:optimize true} (:key this) os)
    (serdes.core/write-Bytes 3  {:optimize true} (:data this) os))
  pb/TypeReflection
  (gettype [this]
    "tendermint.crypto.ProofOp"))

(s/def :tendermint.crypto.ProofOp/type string?)
(s/def :tendermint.crypto.ProofOp/key bytes?)
(s/def :tendermint.crypto.ProofOp/data bytes?)
(s/def ::ProofOp-spec (s/keys :opt-un [:tendermint.crypto.ProofOp/type :tendermint.crypto.ProofOp/key :tendermint.crypto.ProofOp/data ]))
(def ProofOp-defaults {:type "" :key (byte-array 0) :data (byte-array 0) })

(defn cis->ProofOp
  "CodedInputStream to ProofOp"
  [is]
  (->> (tag-map ProofOp-defaults
         (fn [tag index]
             (case index
               1 [:type (serdes.core/cis->String is)]
               2 [:key (serdes.core/cis->Bytes is)]
               3 [:data (serdes.core/cis->Bytes is)]

               [index (serdes.core/cis->undefined tag is)]))
         is)
        (map->ProofOp-record)))

(defn ecis->ProofOp
  "Embedded CodedInputStream to ProofOp"
  [is]
  (serdes.core/cis->embedded cis->ProofOp is))

(defn new-ProofOp
  "Creates a new instance from a map, similar to map->ProofOp except that
  it properly accounts for nested messages, when applicable.
  "
  [init]
  {:pre [(if (s/valid? ::ProofOp-spec init) true (throw (ex-info "Invalid input" (s/explain-data ::ProofOp-spec init))))]}
  (-> (merge ProofOp-defaults init)
      (map->ProofOp-record)))

(defn pb->ProofOp
  "Protobuf to ProofOp"
  [input]
  (cis->ProofOp (serdes.stream/new-cis input)))

(def ^:protojure.protobuf.any/record ProofOp-meta {:type "tendermint.crypto.ProofOp" :decoder pb->ProofOp})

;-----------------------------------------------------------------------------
; ProofOps
;-----------------------------------------------------------------------------
(defrecord ProofOps-record [ops]
  pb/Writer
  (serialize [this os]
    (serdes.complex/write-repeated serdes.core/write-embedded 1 (:ops this) os))
  pb/TypeReflection
  (gettype [this]
    "tendermint.crypto.ProofOps"))

(s/def ::ProofOps-spec (s/keys :opt-un []))
(def ProofOps-defaults {:ops [] })

(defn cis->ProofOps
  "CodedInputStream to ProofOps"
  [is]
  (->> (tag-map ProofOps-defaults
         (fn [tag index]
             (case index
               1 [:ops (serdes.complex/cis->repeated ecis->ProofOp is)]

               [index (serdes.core/cis->undefined tag is)]))
         is)
        (map->ProofOps-record)))

(defn ecis->ProofOps
  "Embedded CodedInputStream to ProofOps"
  [is]
  (serdes.core/cis->embedded cis->ProofOps is))

(defn new-ProofOps
  "Creates a new instance from a map, similar to map->ProofOps except that
  it properly accounts for nested messages, when applicable.
  "
  [init]
  {:pre [(if (s/valid? ::ProofOps-spec init) true (throw (ex-info "Invalid input" (s/explain-data ::ProofOps-spec init))))]}
  (-> (merge ProofOps-defaults init)
      (cond-> (some? (get init :ops)) (update :ops #(map new-ProofOp %)))
      (map->ProofOps-record)))

(defn pb->ProofOps
  "Protobuf to ProofOps"
  [input]
  (cis->ProofOps (serdes.stream/new-cis input)))

(def ^:protojure.protobuf.any/record ProofOps-meta {:type "tendermint.crypto.ProofOps" :decoder pb->ProofOps})

;-----------------------------------------------------------------------------
; PublicKey
;-----------------------------------------------------------------------------
(defrecord PublicKey-record [sum]
  pb/Writer
  (serialize [this os]
    (write-PublicKey-sum  (:sum this) os))
  pb/TypeReflection
  (gettype [this]
    "tendermint.crypto.PublicKey"))

(s/def ::PublicKey-spec (s/keys :opt-un []))
(def PublicKey-defaults {})

(defn cis->PublicKey
  "CodedInputStream to PublicKey"
  [is]
  (->> (tag-map PublicKey-defaults
         (fn [tag index]
             (case index
               1 [:sum {:ed25519 (serdes.core/cis->Bytes is)}]
               2 [:sum {:secp256k1 (serdes.core/cis->Bytes is)}]
               3 [:sum {:sr25519 (serdes.core/cis->Bytes is)}]

               [index (serdes.core/cis->undefined tag is)]))
         is)
        (map->PublicKey-record)))

(defn ecis->PublicKey
  "Embedded CodedInputStream to PublicKey"
  [is]
  (serdes.core/cis->embedded cis->PublicKey is))

(defn new-PublicKey
  "Creates a new instance from a map, similar to map->PublicKey except that
  it properly accounts for nested messages, when applicable.
  "
  [init]
  {:pre [(if (s/valid? ::PublicKey-spec init) true (throw (ex-info "Invalid input" (s/explain-data ::PublicKey-spec init))))]}
  (-> (merge PublicKey-defaults init)
      (convert-PublicKey-sum)
      (map->PublicKey-record)))

(defn pb->PublicKey
  "Protobuf to PublicKey"
  [input]
  (cis->PublicKey (serdes.stream/new-cis input)))

(def ^:protojure.protobuf.any/record PublicKey-meta {:type "tendermint.crypto.PublicKey" :decoder pb->PublicKey})

