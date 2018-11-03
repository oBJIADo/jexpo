const queryStructureLength = 45;

function setArray(array, values, indexes) {
    if (indexes === undefined || values === undefined || indexes.length !== values.length) {
        return;
    }

    indexes.forEach((value, index) => array[value] = values[index]);
    return array
}

function setQueryMapping() {
    let structArray = new Array(queryStructureLength);
    structArray = setArray(
        structArray,
        ['Key', 'Summary', 'Description', 'Epic name', 'Sub-tasks', 'Related to CRs', 'Duplicates'],
        [0, 1, 3, 4, 42, 43, 44]
    );
    structArray = setArray(
        structArray,
        ['comment', 'Comment date', 'Comment author'],
        [36, 35, 34]
    );
    structArray = setArray(
        structArray,
        ['Created', 'Updated', 'Resolved', 'Due date'],
        [8, 10, 11, 12]
    );
    structArray = setArray(
        structArray,
        ['Issue type', 'Status', 'Priority', 'Resolution'],
        [22, 23, 24, 25]
    );
    structArray = setArray(
        structArray,
        ['Assignee', 'Reporter', 'Creator'],
        [31, 32, 33]
    );
    structArray = setArray(
        structArray,
        ['Affects versions', 'Fix versions', 'Components', 'Delivered version', 'DRC number', 'Order number', 'Sprint'],
        [37, 38, 39, 6, 7, 5, 29]
    );
    structArray = setArray(
        structArray,
        ['Labels', 'VSE team', 'Keyword', 'Fix priority'],
        [40, 41, 26, 30]
    );
    structArray = setArray(
        structArray,
        ['Sum progress', 'Sum time spent', 'Sum remaining estimate', 'Sum original estimate'],
        [18, 19, 20, 21]
    );
    structArray = setArray(
        structArray,
        ['Original estimate', 'Remaining estimate', 'Time spent', 'Work ratio', 'Progress'],
        [13, 14, 15, 16, 17]
    );
    return structArray;
}

const url = "http://localhost:8080/";
const queryArray = setQueryMapping();
// const url = "";

export default {
    addresses: {
        tasksUrl: url + 'tasks',
        tasksSearchingUrl: url + 'tasks/search',
        singleTaskUrl: url + 'tasks',
        pageCountUrl: url + 'pages',
        pageCountSearchingUrl: url + 'pages/search',
        filesUrl: url + 'files',
        fileDownloadUrl: 'download',
        employeesUrl: url + 'employees'
    },
    itemsOnPage: 30,
    queryConstants: {
        structure: [
            {
                title: 'GENERAL',
                indexes: [0, 1, 3, 4, 42, 43, 44],
            },
            {
                title: 'COMMENTS',
                indexes: [36, 35, 34],
            },
            {
                title: 'DETAILS',
                indexes: [8, 10, 11, 12],
            },
            {
                title: 'VERSIONS',
                indexes: [22, 23, 24, 25],
            },
            {
                title: 'PEOPLE',
                indexes: [31, 32, 33],
            },
            {
                title: 'DATES',
                indexes: [37, 38, 39, 6, 7, 5, 29],
            },
            {
                title: 'ANOTHER',
                indexes: [40, 41, 26, 30],
            },
            {
                title: 'AMOUNTS',
                indexes: [18, 19, 20, 21],
            },
            {
                title: 'CHARACTERISTICS',
                indexes: [13, 14, 15, 16, 17],
            },
        ],
        queryMapping: queryArray,
    },
    queryStructureLength: queryStructureLength
}