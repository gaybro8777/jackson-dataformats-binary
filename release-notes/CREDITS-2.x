Here are people who have contributed to the development of Jackson JSON processor
binary data formats module
(version numbers in brackets indicate release in which the problem was fixed)

Tatu Saloranta (tatu.saloranta@iki.fi): author

--------------------------------------------------------------------------------
Credits for individual projects, since 2.8.0
--------------------------------------------------------------------------------

Michael Zeng (shotbythought@github)

* Contributed fix for #27: (protobuf) Fixed long deserialization problem for longs of ~13digit length
 (2.8.2)
* Reported #58 (avro): Regression due to changed namespace of inner enum types
 (2.8.8)

Kenji Noguchi (knoguchi@github)

* Reported #70 (protobuf), contributed fix: Can't deserialize packed repeated field
 (2.8.9)

marsqing@github

* Reported #85: (protobuf) _decode32Bits() bug in ProtobufParser
 (2.8.9)
* Reported #94: (protobuf) Should _ensureRoom in ProtobufGenerator.writeString()
 (2.8.10)
* Reported #106 (protobuf), contributed fix for: calling _skipUnknownValue() twice
 (2.8.11 / 2.9.1)
* Reported #116 (protobuf), contributed fix for: Should skip the positive byte
  which is the last byte of an varint
 (2.9.3)
* Reported #126, contributed fix for: always call checkEnd() when skip unknown field
 (2.8.11 / 2.9.3)

baharclerode@github:

* Contributed #14 (avro): Add support for Avro annotations via `AvroAnnotationIntrospector`
 (2.9.0)
* Contributed #15 (avro): Add a way to produce "file" style Avro output
 (2.9.0)
* Contributed #57 (avro): Add support for @Stringable annotation
 (2.9.0)
* Contributed #59 (avro): Add support for @AvroAlias annotation for Record/Enum name evolution
 (2.9.0)
* Contributed #60 (avro): Add support for `@Union` and polymorphic types
 (2.9.0)

Eldad Rudich (eldadru@github)

* Reported #68 (proto): Getting "type not supported as root type by protobuf" for serialization
  of short and UUID types
 (2.9.0)

philipa@github

* Reported #114 (cbor), contributed fix for: copyStructure(): avoid duplicate tags
  when copying tagged binary
 (2.9.3)

Jacek Lach (JacekLach@github)

* Reported #124: Invalid value returned for negative int32 where the absolute value is > 2^31 - 1
 (2.9.3)

Leo Wang (wanglingsong@github)

* Reported #135: Infinite sequence of `END_OBJECT` tokens returned at end of streaming read
 (2.9.6)

Michael Milkin (mmilkin@github)
* Reported, Contributed fix for #142: (ion) `IonParser.getNumberType()` returns `null`
  for `IonType.FLOAT`
 (2.9.7)

Guido Medina (guidomedina@github)
* Reported #153: (smile) Unable to set a compression input/output decorator to a `SmileFactory`
 (2.9.8)

Alexander Cyon (Sajjon@github)
* Reported #159: (cbor) Some short UTF Strings encoded using non-canonical form
 (2.9.9)

Łukasz Dziedziak (lukidzi@github)
* Reported, contributed fix for #161: (avro) Deserialize from newer version to older
   one throws NullPointerException
 (2.9.9)

Carter Kozak (cakofony@github)
* Reported, suggested fix for #155: Inconsistent support for FLUSH_PASSED_TO_STREAM
 (2.10.0)

Fernando Raganhan Barbosa (raganhan@github)
* Suggested #163: (ion) Update `ion-java` dependency
 (2.10.0)

Juliana Amorim (amorimjuliana@github)
* Reported #168: (avro) `JsonMappingException` for union types with multiple Record types
 (2.10.0)

Marcos Passos (marcospassos@github)
* Contributed fix for #168: (avro) `JsonMappingException` for union types with multiple Record types
 (2.10.0)
* Contributed fix for #173: (Avro) Improve Union type serialization performance
 (2.10.0)

John (iziamos@github)
* Reported, suggested fix for #178: Fix issue wit input offsets when parsing CBOR from `InputStream`
 (2.10.0)
