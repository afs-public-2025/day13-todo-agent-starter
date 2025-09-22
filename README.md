# Todo Agent Starter

```json
{
  "servers": {
    "remote-example": {
      "url": "http://localhost:8080/sse"
    }
  }
}
```

or

```json
{
  "mcpServers": {
    "remote-example": {
      "url": "http://localhost:8080/sse"
    }
  }
}
```

## RAG Resource

- [Retrieval Augmented Generation](https://docs.spring.io/spring-ai/reference/api/retrieval-augmented-generation.html)
- [Vector Databases](https://docs.spring.io/spring-ai/reference/api/vectordbs.html)
    - [SimpleVectorStore](https://github.com/spring-projects/spring-ai/blob/main/spring-ai-vector-store/src/main/java/org/springframework/ai/vectorstore/SimpleVectorStore.java)
- [Embeddings Model API](https://docs.spring.io/spring-ai/reference/api/embeddings.html)
    - [Transformers (ONNX) Embeddings](https://docs.spring.io/spring-ai/reference/api/embeddings/onnx.html)
- [Advisors API](https://docs.spring.io/spring-ai/reference/api/advisors.html)

Database Vector type:

- MariaDB: https://mariadb.org/projects/mariadb-vector/
- MySQL: https://dev.mysql.com/doc/refman/9.3/en/vector.html