URLs
----------------------------------------------------------------------------------------------------

/                                  : redirect to /#!/tasks

/#!/tasks                          : UserViewTasks
/#!/tasks/{taskId}                 : UserViewTasks
/#!/projects                       : UserViewProjects
/#!/projects/{projectName}         : UserViewProjects
/#!/actions                        : UserViewActions
/#!/actions/{actionName}           : UserViewActions

/rest/tasks                        : TasksResource (read [GET])
/rest/tasks/{taskId}               : TaskResource (read [GET])
/rest/projects                     : ProjectsResource (read [GET], create [POST])
/rest/projects/{projectName}       : ProjectResource (read [GET], update [PUT], delete [DELETE])
                                     - /acl (read [GET], update [PUT], delete [DELETE])
/rest/systems                      : SystemsResource (read [GET], create [POST])
/rest/systems/{systemName}         : SystemResource (read [GET], update [PUT], delete [DELETE])
                                     - /acl (read [GET], update [PUT], delete [DELETE])
/rest/collections                  : CollectionsResource (read [GET], create [POST])
/rest/collections/{collectionName} : CollectionResource (read [GET], update [PUT], delete [DELETE])
                                     - /acl (read [GET], update [PUT], delete [DELETE])
                                     - /schema (read [GET], update [PUT])
                                     - /xsd (read [GET])
                                     - /documents (read [GET], create [POST])
/rest/documents                    : DocumentsResource (query [GET])
/rest/documents/{documentId}       : DocumentResource (read [GET], update [PUT], delete [DELETE])
                                     - /acl (read [GET], update [PUT], delete [DELETE])
/rest/actions                      : ActionsResource (read [GET], create [POST])
/rest/actions/{actionName}         : ActionResource (read [GET], update [PUT], delete [DELETE])
                                     - /acl (read [GET], update [PUT], delete [DELETE])
