请求：
请求分页数据时，请求的条件必须以 json 格式在 post body 内，里面必须包含下面这个数据项：
  "pageParam": {
    "offset": 0,
    "pageNum": 0,
    "pageSize": 0,
    "roundMaxId": 0,
    "roundMinId": 0,
    "rows": 0
  },
详见 swagger 参数；其中含义：
    offset: MySQL查询中的offset，一般不使用
    rows: MySQL查询中的rows，一般不使用
    pageNum: 普通分页模式下，请求的页码
    pageSize: 普通分页和快速更新模式下，请求的每页数量
    roundMinId: 快速更新分页模式下，本轮请求的最小Id，第一次请求不传
    roundMaxId: 快速更新分页模式下，本轮请求的最大Id，第一次请求不传

响应：
普通分页模式下，响应数据包含下列内容：
    "requestPageNum": 1,
    "requestPageSize": 10,
    "list": [
    ],
    "total": "9",
    "pageNum": 1,
    "pages": 1
其中含义：
    "requestPageNum": 请求的页码（怎么请求怎么返回）
    "requestPageSize": 请求的每页的数量（怎么请求怎么返回）
    "list": 数据列表
    "total": 符合条件的数据总数
    "pageNum": 实际返回的页码
    "pages": 总页数

快速更新模式下，响应数据包含下列内容：
    "requestRoundMinId": null,
    "requestRoundMaxId": null,
    "requestPageSize": 10,
    "list": [
    ],
    "roundMinId": "92245664100126720",
    "roundMaxId": "155631880245481472",
    "count": 9
其中含义：
    "requestRoundMinId": 请求的本轮请求的最小Id（怎么请求怎么返回）
    "requestRoundMaxId": 请求的本轮请求的最大Id（怎么请求怎么返回）
    "requestPageSize": 请求的每页的数量（怎么请求怎么返回）
    "list": 数据列表
    "roundMinId": 本轮请求的最小Id（下次请求时使用）
    "roundMaxId": 本轮请求的最大Id（下次请求时使用）
    "count": 返回的数据个数

