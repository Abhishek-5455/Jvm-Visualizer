import axios from 'axios';

const API = 'http://localhost:8080/api/bytecode';

export const analyzeCode = async (
    sourceCode
) => {
    const response = await axios.post(API, { 
        className: 'Demo',
        sourceCode: sourceCode
     }
    );

    return response.data;
}