const queryStructureLength = 45;

function containsIndex(indexes, currentIndex) {
    let result = false;
    indexes.forEach(value => {
        if (+value === +currentIndex) {
            result = true;
        }
    });
    return result;
}

function setQueryStructureElements(names, indexes, trueValueIndexes) {
    //todo: set all bool values to the right in this case
    if (indexes === undefined || names === undefined || indexes.length !== names.length) {
        return [];
    }

    let elementsArray = Array(names.length);
    if (trueValueIndexes !== undefined && trueValueIndexes.length > 0) {
        for (let i = 0; i < names.length; i++) {
            elementsArray[i] = {
                name: names[i],
                index: indexes[i],
                value: containsIndex(trueValueIndexes, indexes[i])
            }
        }
    } else {
        for (let i = 0; i < names.length; i++) {
            elementsArray[i] = {
                name: names[i],
                index: indexes[i],
                value: false
            }
        }
    }
    return elementsArray
}

function getQueryStructure(indexes) {
    return [
        {
            title: 'GENERAL',
            elements: setQueryStructureElements(
                ['Key', 'Summary', 'Description', 'Epic name', 'Sub-tasks', 'Related to CRs', 'Duplicates'],
                [0, 1, 3, 4, 42, 43, 44],
                indexes
            )
        },
        {
            title: 'COMMENTS',
            elements: setQueryStructureElements(
                ['comment', 'Comment date', 'Comment author'],
                [36, 35, 34],
                indexes
            )
        },
        {
            title: 'DATES',
            elements: setQueryStructureElements(
                ['Created', 'Updated', 'Resolved', 'Due date'],
                [8, 10, 11, 12],
                indexes
            )
        },
        {
            title: 'DETAILS',
            elements: setQueryStructureElements(
                ['Issue type', 'Status', 'Priority', 'Resolution'],
                [22, 23, 24, 25],
                indexes
            )
        },
        {
            title: 'PEOPLE',
            elements: setQueryStructureElements(
                ['Assignee', 'Reporter', 'Creator'],
                [31, 32, 33],
                indexes
            )
        },
        {
            title: 'VERSIONS',
            elements: setQueryStructureElements(
                ['Affects versions', 'Fix versions', 'Components', 'Delivered version', 'DRC number', 'Order number', 'Sprint'],
                [37, 38, 39, 6, 7, 5, 29],
                indexes
            )
        },
        {
            title: 'ANOTHER',
            elements: setQueryStructureElements(
                ['Labels', 'VSE team', 'Keyword', 'Fix priority'],
                [40, 41, 26, 30],
                indexes
            )
        },
        {
            title: 'AMOUNTS',
            elements: setQueryStructureElements(
                ['Sum progress', 'Sum time spent', 'Sum remaining estimate', 'Sum original estimate'],
                [18, 19, 20, 21],
                indexes
            )
        },
        {
            title: 'CHARACTERISTICS',
            elements: setQueryStructureElements(
                ['Original estimate', 'Remaining estimate', 'Time spent', 'Work ratio', 'Progress'],
                [13, 14, 15, 16, 17],
                indexes
            )
        }
    ];
}

const url = "http://localhost:8080/";
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
    queryConstants: getQueryStructure,
    queryStructureLength: queryStructureLength
}