package com.app.notesapplication

class Notes {

    var nodeId:Int? = null
    var nodeName:String? = null
    var nodeDescription:String? = null

    constructor(nodeId:Int, nodeName:String, nodeDescription:String) {
        this.nodeId = nodeId
        this.nodeName = nodeName
        this.nodeDescription = nodeDescription
    }
}